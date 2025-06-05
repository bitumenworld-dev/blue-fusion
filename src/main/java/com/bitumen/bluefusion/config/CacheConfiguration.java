package com.bitumen.bluefusion.config;

import com.bitumen.bluefusion.domain.FuelIssuanceType;
import com.bitumen.bluefusion.domain.Make;
import com.bitumen.bluefusion.domain.MakeModel;
import java.time.Duration;
import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;
import org.hibernate.cache.jcache.ConfigSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.*;
import tech.jhipster.config.JHipsterProperties;
import tech.jhipster.config.cache.PrefixedKeyGenerator;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private GitProperties gitProperties;
    private BuildProperties buildProperties;
    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache = jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(
                Object.class,
                Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries())
            )
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build()
        );
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, com.bitumen.bluefusion.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, com.bitumen.bluefusion.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, com.bitumen.bluefusion.domain.User.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.Authority.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.User.class.getName() + ".authorities");
            createCache(cm, com.bitumen.bluefusion.domain.AssetPlant.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.AssetPlantServiceReading.class.getName());
            createCache(cm, FuelIssuanceType.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.AssetPlantPhoto.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.ContractDivision.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.Site.class.getName());
            createCache(cm, Make.class.getName());
            createCache(cm, MakeModel.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.Company.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.PlantCategory.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.PlantSubcategory.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.Workshop.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.SiteContract.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.Employee.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.FuelTransactionHeader.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.FuelTransactionLine.class.getName());
            createCache(cm, com.bitumen.bluefusion.domain.FuelPump.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        } else {
            cm.createCache(cacheName, jcacheConfiguration);
        }
    }

    @Autowired(required = false)
    public void setGitProperties(GitProperties gitProperties) {
        this.gitProperties = gitProperties;
    }

    @Autowired(required = false)
    public void setBuildProperties(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new PrefixedKeyGenerator(this.gitProperties, this.buildProperties);
    }
}
