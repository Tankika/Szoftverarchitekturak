package hu.bme.archi.issue.bean;

import org.joda.beans.Bean;
import org.joda.beans.BeanDefinition;
import org.joda.beans.PropertyDefinition;

import hu.bme.archi.issue.domain.Priority;
import hu.bme.archi.issue.domain.Severity;
import hu.bme.archi.issue.domain.Status;
import hu.bme.archi.issue.domain.Type;
import java.util.Map;
import java.util.Set;
import org.joda.beans.BeanBuilder;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

@BeanDefinition
public class ConstantsResponse implements Bean {
    
    @PropertyDefinition
    private Type[] types;
    
    @PropertyDefinition
    private Priority[] priorities;
    
    @PropertyDefinition
    private Severity[] severities;
    
    @PropertyDefinition
    private Status[] statuses;

    //------------------------- AUTOGENERATED START -------------------------
    ///CLOVER:OFF
    /**
     * The meta-bean for {@code ConstantsResponse}.
     * @return the meta-bean, not null
     */
    public static ConstantsResponse.Meta meta() {
        return ConstantsResponse.Meta.INSTANCE;
    }

    static {
        JodaBeanUtils.registerMetaBean(ConstantsResponse.Meta.INSTANCE);
    }

    @Override
    public ConstantsResponse.Meta metaBean() {
        return ConstantsResponse.Meta.INSTANCE;
    }

    @Override
    public <R> Property<R> property(String propertyName) {
        return metaBean().<R>metaProperty(propertyName).createProperty(this);
    }

    @Override
    public Set<String> propertyNames() {
        return metaBean().metaPropertyMap().keySet();
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the types.
     * @return the value of the property
     */
    public Type[] getTypes() {
        return types;
    }

    /**
     * Sets the types.
     * @param types  the new value of the property
     */
    public void setTypes(Type[] types) {
        this.types = types;
    }

    /**
     * Gets the the {@code types} property.
     * @return the property, not null
     */
    public final Property<Type[]> types() {
        return metaBean().types().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the priorities.
     * @return the value of the property
     */
    public Priority[] getPriorities() {
        return priorities;
    }

    /**
     * Sets the priorities.
     * @param priorities  the new value of the property
     */
    public void setPriorities(Priority[] priorities) {
        this.priorities = priorities;
    }

    /**
     * Gets the the {@code priorities} property.
     * @return the property, not null
     */
    public final Property<Priority[]> priorities() {
        return metaBean().priorities().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the severities.
     * @return the value of the property
     */
    public Severity[] getSeverities() {
        return severities;
    }

    /**
     * Sets the severities.
     * @param severities  the new value of the property
     */
    public void setSeverities(Severity[] severities) {
        this.severities = severities;
    }

    /**
     * Gets the the {@code severities} property.
     * @return the property, not null
     */
    public final Property<Severity[]> severities() {
        return metaBean().severities().createProperty(this);
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the statuses.
     * @return the value of the property
     */
    public Status[] getStatuses() {
        return statuses;
    }

    /**
     * Sets the statuses.
     * @param statuses  the new value of the property
     */
    public void setStatuses(Status[] statuses) {
        this.statuses = statuses;
    }

    /**
     * Gets the the {@code statuses} property.
     * @return the property, not null
     */
    public final Property<Status[]> statuses() {
        return metaBean().statuses().createProperty(this);
    }

    //-----------------------------------------------------------------------
    @Override
    public ConstantsResponse clone() {
        return JodaBeanUtils.cloneAlways(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj != null && obj.getClass() == this.getClass()) {
            ConstantsResponse other = (ConstantsResponse) obj;
            return JodaBeanUtils.equal(getTypes(), other.getTypes()) &&
                    JodaBeanUtils.equal(getPriorities(), other.getPriorities()) &&
                    JodaBeanUtils.equal(getSeverities(), other.getSeverities()) &&
                    JodaBeanUtils.equal(getStatuses(), other.getStatuses());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = getClass().hashCode();
        hash = hash * 31 + JodaBeanUtils.hashCode(getTypes());
        hash = hash * 31 + JodaBeanUtils.hashCode(getPriorities());
        hash = hash * 31 + JodaBeanUtils.hashCode(getSeverities());
        hash = hash * 31 + JodaBeanUtils.hashCode(getStatuses());
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder(160);
        buf.append("ConstantsResponse{");
        int len = buf.length();
        toString(buf);
        if (buf.length() > len) {
            buf.setLength(buf.length() - 2);
        }
        buf.append('}');
        return buf.toString();
    }

    protected void toString(StringBuilder buf) {
        buf.append("types").append('=').append(JodaBeanUtils.toString(getTypes())).append(',').append(' ');
        buf.append("priorities").append('=').append(JodaBeanUtils.toString(getPriorities())).append(',').append(' ');
        buf.append("severities").append('=').append(JodaBeanUtils.toString(getSeverities())).append(',').append(' ');
        buf.append("statuses").append('=').append(JodaBeanUtils.toString(getStatuses())).append(',').append(' ');
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-bean for {@code ConstantsResponse}.
     */
    public static class Meta extends DirectMetaBean {
        /**
         * The singleton instance of the meta-bean.
         */
        static final Meta INSTANCE = new Meta();

        /**
         * The meta-property for the {@code types} property.
         */
        private final MetaProperty<Type[]> types = DirectMetaProperty.ofReadWrite(
                this, "types", ConstantsResponse.class, Type[].class);
        /**
         * The meta-property for the {@code priorities} property.
         */
        private final MetaProperty<Priority[]> priorities = DirectMetaProperty.ofReadWrite(
                this, "priorities", ConstantsResponse.class, Priority[].class);
        /**
         * The meta-property for the {@code severities} property.
         */
        private final MetaProperty<Severity[]> severities = DirectMetaProperty.ofReadWrite(
                this, "severities", ConstantsResponse.class, Severity[].class);
        /**
         * The meta-property for the {@code statuses} property.
         */
        private final MetaProperty<Status[]> statuses = DirectMetaProperty.ofReadWrite(
                this, "statuses", ConstantsResponse.class, Status[].class);
        /**
         * The meta-properties.
         */
        private final Map<String, MetaProperty<?>> metaPropertyMap$ = new DirectMetaPropertyMap(
                this, null,
                "types",
                "priorities",
                "severities",
                "statuses");

        /**
         * Restricted constructor.
         */
        protected Meta() {
        }

        @Override
        protected MetaProperty<?> metaPropertyGet(String propertyName) {
            switch (propertyName.hashCode()) {
                case 110844025:  // types
                    return types;
                case 978350402:  // priorities
                    return priorities;
                case -987490213:  // severities
                    return severities;
                case 1318692896:  // statuses
                    return statuses;
            }
            return super.metaPropertyGet(propertyName);
        }

        @Override
        public BeanBuilder<? extends ConstantsResponse> builder() {
            return new DirectBeanBuilder<ConstantsResponse>(new ConstantsResponse());
        }

        @Override
        public Class<? extends ConstantsResponse> beanType() {
            return ConstantsResponse.class;
        }

        @Override
        public Map<String, MetaProperty<?>> metaPropertyMap() {
            return metaPropertyMap$;
        }

        //-----------------------------------------------------------------------
        /**
         * The meta-property for the {@code types} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Type[]> types() {
            return types;
        }

        /**
         * The meta-property for the {@code priorities} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Priority[]> priorities() {
            return priorities;
        }

        /**
         * The meta-property for the {@code severities} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Severity[]> severities() {
            return severities;
        }

        /**
         * The meta-property for the {@code statuses} property.
         * @return the meta-property, not null
         */
        public final MetaProperty<Status[]> statuses() {
            return statuses;
        }

        //-----------------------------------------------------------------------
        @Override
        protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 110844025:  // types
                    return ((ConstantsResponse) bean).getTypes();
                case 978350402:  // priorities
                    return ((ConstantsResponse) bean).getPriorities();
                case -987490213:  // severities
                    return ((ConstantsResponse) bean).getSeverities();
                case 1318692896:  // statuses
                    return ((ConstantsResponse) bean).getStatuses();
            }
            return super.propertyGet(bean, propertyName, quiet);
        }

        @Override
        protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
            switch (propertyName.hashCode()) {
                case 110844025:  // types
                    ((ConstantsResponse) bean).setTypes((Type[]) newValue);
                    return;
                case 978350402:  // priorities
                    ((ConstantsResponse) bean).setPriorities((Priority[]) newValue);
                    return;
                case -987490213:  // severities
                    ((ConstantsResponse) bean).setSeverities((Severity[]) newValue);
                    return;
                case 1318692896:  // statuses
                    ((ConstantsResponse) bean).setStatuses((Status[]) newValue);
                    return;
            }
            super.propertySet(bean, propertyName, newValue, quiet);
        }

    }

    ///CLOVER:ON
    //-------------------------- AUTOGENERATED END --------------------------
}
