package me.zhangjin.springboot.demo.basic.spec;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Iterables.toArray;

/**
 * 结合 Specification 和 自定义 Repository 实现来定制一个自动模糊查询
 * 即对于任意的实体对象进行查询，对象里有几个值我们就查几个
 * 当值为字符型时我们就自动 like 查询
 * 其余的类型使用自动等于查询，没有值我们就查询全部
 */
public class CustomerSpecs {

    /**
     * 定义一个返回值为 Specification 的方法 byAuto，这是使用的是泛型 T，所以这个 Specification 是可以用于任意的实体类的
     * 它接受的参数是 entityManager 和当前的包含值作为查询条件的实体类对象
     *
     * @param entityManager
     * @param example
     * @param <T>
     * @return
     */
    public static <T> Specification<T> byAuto(final EntityManager entityManager, final T example) {

        // 获得当前实体类对象类的类型
        final Class<T> type = (Class<T>) example.getClass();

        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                // 新建 Predicate 列表存储构造的查询条件
                List<Predicate> predicates = new ArrayList<>();

                // 获得实体类的 EntityType，我们可以从 EntityType 获得实体类的属性
                EntityType<T> entityType = entityManager.getMetamodel().entity(type);

                // 对实体类对象的所有属性做循环
                for (Attribute<T, ?> attr : entityType.getDeclaredAttributes()) {

                    // 获得实体类对象某一个属性的值
                    Object attrValue = getValue(example, attr);
                    if (attrValue != null) {

                        // 当前属性值为字符串类型的时候
                        if (attr.getJavaType() == String.class) {

                            // 若当前字符不为空的情况下
                            if (!StringUtils.isEmpty(attrValue)) {

                                // 构造当前属性 like 属性值查询条件，并添加到条件列表中
                                predicates.add(cb.like(root.get(attribute(entityType, attr.getName(), String.class)), pattern((String) attrValue)));
                            } else {

                                // 其余情况下，构造属性和属性值 equal 查询条件，并添加到条件列表中
                                predicates.add(cb.equal(root.get(attribute(entityType, attr.getName(), attrValue.getClass())), attrValue));
                            }
                        }
                    }
                }

                // 将条件列表转换成 Predicate
                return predicates.isEmpty() ? cb.conjunction() : cb.and(toArray(predicates, Predicate.class));
            }

            /**
             * 通过反射获得实体类对象对应属性的属性值
             * @param example
             * @param attr
             * @param <T>
             * @return
             */
            private <T> Object getValue(T example, Attribute<T, ?> attr) {
                return ReflectionUtils.getField((Field) attr.getJavaMember(), example);
            }

            /**
             * 获得实体类的当前属性的 SingularAttribute，SingularAttribute 包含的是实体类的某个单独属性
             * @param entityType
             * @param fieldName
             * @param fieldClass
             * @param <T>
             * @param <E>
             * @return
             */
            private <T, E> SingularAttribute<T, E> attribute(EntityType<T> entityType, String fieldName, Class<E> fieldClass) {
                return entityType.getDeclaredSingularAttribute(fieldName, fieldClass);
            }

        };

    }

    /**
     * 构造 like 的查询模式，即前后加 %
     *
     * @param str
     * @return
     */
    private static String pattern(String str) {
        return "%" + str + "%";
    }

}
