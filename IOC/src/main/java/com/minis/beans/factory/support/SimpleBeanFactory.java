package com.minis.beans.factory.support;


import com.minis.beans.*;
import com.minis.beans.factory.BeanFactory;
import com.minis.beans.factory.config.ConstructorArgumentValue;
import com.minis.beans.factory.config.ConstructorArgumentValues;
import com.minis.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//简易bean工厂
public class SimpleBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory, BeanDefinitionRegistry {
    //ConcurrentHashMap会锁住一小部分，保证线程安全。而不会锁住整个线程。锁的粒度较小
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    //用一个arraylist装bean的bean的别名
    private List<String> beanDefinitionNames = new ArrayList<>();
    //bean毛坯实例，为了解决循环依赖
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    //无参构造方法
    public SimpleBeanFactory() {
    }

    //刷新，包装方法，使用增强for对所有的Bean调用一次getBean方法，用一个方法将容器内的所有Bean的实例创建
    public void refresh() {
        for (String beanName : beanDefinitionNames) {
            try {
                getBean(beanName);
            } catch (BeansException e) {
                e.printStackTrace();
            }
        }
    }


    //获取bean实例，判断是否已经有实例。将创建bean实例的职能提取到插入createBean方法中
    public Object getBean(String beanName) throws BeansException {
        Object singleton = this.getSingleton(beanName);
        //如果没此实例则在毛坯实例中添加实例
        if (singleton == null) {
            singleton = this.earlySingletonObjects.get(beanName);

            if (singleton == null) {
                //打印没有这个对应名称的bean，通过createBean方法创建实例并注册
                System.out.println("get bean null -------------- " + beanName);
                BeanDefinition bd = beanDefinitionMap.get(beanName);
                singleton = createBean(bd);
                this.registerBean(beanName, singleton);

                //BeanPostProcessor
                //step 1 : postProcessBeforeInitialization
                //step 2 : afterPropertiesSet
                //step 3 : init-method
                //step 4 : postProcessAfterInitialization。
            }

        }
        if (singleton == null) {
            throw new BeansException("bean is null.");
        }
        return singleton;
    }

    //判断是否包含此名称的bean实例
    @Override
    public boolean ContainsBean(String name) {
        return containsSingleton(name);
    }


    //注册该bean实例
    public void registerBean(String beanName, Object obj) {
        this.registerSingleton(beanName, obj);

        //BeanPostProcessor
    }

    //注册bean该bean实例的定义，将别名与bean绑定
    @Override
    public void registerBeanDefinition(String name, BeanDefinition bd) {
        //将该键值对添加进bean实例map中
        this.beanDefinitionMap.put(name, bd);
        //bean实例别名集合
        this.beanDefinitionNames.add(name);
        //判断是否是延迟加载的bean，如果判断为假则立即加载
        if (!bd.isLazyInit()) {
            try {
                getBean(name);
            } catch (BeansException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    //删除bean定义
    @Override
    public void removeBeanDefinition(String name) {
        this.beanDefinitionMap.remove(name);
        this.beanDefinitionNames.remove(name);
        this.removeSingleton(name);

    }

    //获取bean定义数据
    @Override
    public BeanDefinition getBeanDefinition(String name) {
        return this.beanDefinitionMap.get(name);
    }

    //判断是否包含有某个属性别名
    @Override
    public boolean containsBeanDefinition(String name) {
        return this.beanDefinitionMap.containsKey(name);
    }

    //判断是否有这个单例
    @Override
    public boolean isSingleton(String name) {
        return this.beanDefinitionMap.get(name).isSingleton();
    }

    //判断是否有着个属性
    @Override
    public boolean isPrototype(String name) {
        return this.beanDefinitionMap.get(name).isPrototype();
    }


    //获取该bean实例中的bean类
    @Override
    public Class<?> getType(String name) {
        return this.beanDefinitionMap.get(name).getClass();
    }

    //加载bean实例
    private Object createBean(BeanDefinition bd) {
        Class<?> clz = null;
        //创建bd实例对象
        Object obj = doCreateBean(bd);
        //将bd对象的id和对象数据定义，放进毛坯实例中
        this.earlySingletonObjects.put(bd.getId(), obj);

        try {
            //类反射加载通过类名加载类
            clz = Class.forName(bd.getClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //处理属性值方法
        handleProperties(bd, clz, obj);

        return obj;


    }

    //创建bean毛坯实例
    //doCreateBean创建毛胚实例，仅仅调用构造方法，没有进行属性处理
    private Object doCreateBean(BeanDefinition bd) {
        Class<?> clz ;
        Object obj = null;
        Constructor<?> con ;

        try {
            //通过类名加载类
            clz = Class.forName(bd.getClassName());

            //handle constructor
            //处理构造函数参数
            ConstructorArgumentValues constructorArgumentValues = bd.getConstructorArgumentValues();
            //isEmpty返回值取反，判断是否为空
            if (!constructorArgumentValues.isEmpty()) {
                //通过构造函数构建一个数组类，长度为构造参数的数量，这个数组可以为后面提供，构造参数类的类型
                Class<?>[] paramTypes = new Class<?>[constructorArgumentValues.getArgumentCount()];
                //创建一个对象数组用来装参数值对象，数组的长度为参数值的数量
                Object[] paramValues = new Object[constructorArgumentValues.getArgumentCount()];
                //循环遍历参数值的数量
                for (int i = 0; i < constructorArgumentValues.getArgumentCount(); i++) {
                    //通过获取索引号的方法给每个参数值赋值.
                    ConstructorArgumentValue constructorArgumentValue = constructorArgumentValues.getIndexedArgumentValue(i);
                    //通过获取类型方法（类型为字符串类型），是否匹配来确定，类的数据类型。
                    if ("String".equals(constructorArgumentValue.getType()) || "java.lang.String".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    } else if ("Integer".equals(constructorArgumentValue.getType()) || "java.lang.Integer".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = Integer.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue());
                    } else if ("int".equals(constructorArgumentValue.getType())) {
                        paramTypes[i] = int.class;
                        paramValues[i] = Integer.valueOf((String) constructorArgumentValue.getValue()).intValue();
                    } else {
                        paramTypes[i] = String.class;
                        paramValues[i] = constructorArgumentValue.getValue();
                    }
                }
                try {
                    //构造函数的类的类型
                    con = clz.getConstructor(paramTypes);
                    //参数值的数据类型
                    obj = con.newInstance(paramValues);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }  catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                //将对象实例化（无参构造方法）
                obj = clz.getDeclaredConstructor().newInstance();
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
         catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        //打印bean的id和类名，和对象的String类型
        System.out.println(bd.getId() + " bean created. " + bd.getClassName() + " : " + obj.toString());

        return obj;

    }
        //补全毛胚实例属性
    private void handleProperties(BeanDefinition bd, Class<?> clz, Object obj) {
        //handle properties
        //打印bean的id
        System.out.println("handle properties for bean : " + bd.getId());
        //获取属性的参数值
        PropertyValues propertyValues = bd.getPropertyValues();
        //isEmpty取反，判空。
        if (!propertyValues.isEmpty()) {
            //遍历属性值的值
            for (int i = 0; i < propertyValues.size(); i++) {
                //通过获取该索引号，给当前的属性值赋值
                PropertyValue propertyValue = propertyValues.getPropertyValueList().get(i);
                //获取属性的，标签的参数值
                String pName = propertyValue.getName();
                String pType = propertyValue.getType();
                Object pValue = propertyValue.getValue();
                boolean isRef = propertyValue.getIsRef();
                //类的类型数组
                Class<?>[] paramTypes = new Class<?>[1];
                //参数值的对象数组
                Object[] paramValues = new Object[1];
                //判断是否引用其他bean,并且判断该类的类型。
                if (!isRef) {
                    if ("String".equals(pType) || "java.lang.String".equals(pType)) {
                        paramTypes[0] = String.class;
                    } else if ("Integer".equals(pType) || "java.lang.Integer".equals(pType)) {
                        paramTypes[0] = Integer.class;
                    } else if ("int".equals(pType)) {
                        paramTypes[0] = int.class;
                    } else {
                        paramTypes[0] = String.class;
                    }

                    paramValues[0] = pValue;
                } else { //is ref, create the dependent beans
                    try {
                        paramTypes[0] = Class.forName(pType);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    try {
                        paramValues[0] = getBean((String) pValue);
                    } catch (BeansException e) {
                        e.printStackTrace();
                    }
                }
                //创建bean依赖的setting方法，并且将首字母大写（驼峰命名）
                String methodName = "set" + pName.substring(0, 1).toUpperCase() + pName.substring(1);


                Method method = null;
                try {
                    //getMethod返回clz类的public方法
                    method = clz.getMethod(methodName, paramTypes);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
                try {
                    //使用invoke调用方法参数。因为方法在运行前无法知道调用哪个对象
                    method.invoke(obj, paramValues);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


            }
        }

    }
}