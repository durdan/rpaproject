package com.edatablock.rpa.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.edatablock.rpa.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.Organization.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.OrgEmailConfig.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.Client.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.Client.class.getName() + ".outputTemplates", jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.Client.class.getName() + ".inputTemplates", jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.ClientEmailDomain.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.InputTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.OutputTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.TemplateFields.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.EmailMessages.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.EmailMessages.class.getName() + ".emailAttachments", jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.EmailAttachment.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.EmailProcessingError.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.UploadFiles.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.FileForOCRProcessing.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.Transaction.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.Transaction.class.getName() + ".clientDataOcrs", jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.OcrProcessingError.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.ClientDataOcr.class.getName(), jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.ClientDataOcr.class.getName() + ".outputTemplates", jcacheConfiguration);
            cm.createCache(com.edatablock.rpa.domain.FileToFtp.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
