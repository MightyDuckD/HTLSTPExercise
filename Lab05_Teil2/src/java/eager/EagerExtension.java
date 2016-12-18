/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eager;
 
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;
 
public class EagerExtension implements Extension {
    private final List<Bean<?>> eagerBeansList = new ArrayList<>();
 
    public <T> void collect(@Observes ProcessBean<T> event) {
        if (event.getAnnotated().isAnnotationPresent(Eager.class)
            && event.getAnnotated().isAnnotationPresent(ApplicationScoped.class)) {
            eagerBeansList.add(event.getBean());
        }
    }
 
    public void load(@Observes AfterDeploymentValidation event, BeanManager beanManager) {
        for (Bean<?> bean : eagerBeansList) {
            // note: toString() is important to instantiate the bean
            beanManager.getReference(bean, bean.getBeanClass(), beanManager.createCreationalContext(bean)).toString();
        }
    }
}
