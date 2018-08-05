package com.learning;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
		entityManagerFactoryRef="entityManagerFactorySecondary",
		transactionManagerRef="transactionManagerSecondary",
		basePackages= { "com.learning.dao.prod" })
public class RepositorySecondaryConfig {
	
    @Autowired
    private JpaProperties jpaProperties;
    
	@Autowired
    @Qualifier("secondaryDS")
    private DataSource secondaryDS;
	
	@Bean(name = "entityManagerFactorySecondary")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(EntityManagerFactoryBuilder builder) {
        return builder
                //set datasource
                .dataSource(secondaryDS)
                //set hibernate property
                .properties(getVendorProperties())
                //set @Entity class
                .packages(new String[] { "com.learning.entity" })
                // Spring will inject EntityManagerFactory to Repository. 
                // then Repository can used EntityManager can execute CRUD operation for Entity
                .persistenceUnit("secondaryPersistenceUnit")
                .build();

    }
	
	/**
	 * here is 2.0.x difference with 1.5.x, 
	 * 1.5.x getVerdorProperties's parameter is datasource
	 * in 2.0.x the config has in yml or properties configed, here only need new HibernateSettings Object
	 */
    private Map<String, Object> getVendorProperties() {
        return jpaProperties.getHibernateProperties(new HibernateSettings());
    }
    
//	@Bean
//    @Primary
//    public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary() {
//        LocalContainerEntityManagerFactoryBean em
//          = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(primaryDS);
//        em.setPackagesToScan(
//          new String[] { "com.learning.entity" });
// 
//        HibernateJpaVendorAdapter vendorAdapter
//          = new HibernateJpaVendorAdapter();
//        em.setJpaVendorAdapter(vendorAdapter);
//        HashMap<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
//        em.setJpaPropertyMap(properties);
// 
//        return em;
//    }
    
    @Primary
    @Bean
    public PlatformTransactionManager transactionManagerSecondary(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager transactionManager
          = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactoryPrimary(builder).getObject());
        return transactionManager;
    }
}
