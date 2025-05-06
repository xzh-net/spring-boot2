package net.xzh.geode.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

@EnableGemfireRepositories(basePackages = "net.xzh.geode.repository")
@EnableEntityDefinedRegions(basePackages = "net.xzh.geode.entity")
@Configuration
public class GemFireConfig {
    @EnablePdx
    static class PdxConfig {}
}