//package uz.pdp.fastfoodapp.configutation;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.util.StringUtils;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.resource.ResourceResolver;
//import org.springframework.web.servlet.resource.ResourceResolverChain;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//
//
//@Configuration
//public class WebMvcConfig implements WebMvcConfigurer {
//
//
//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("http://127.0.0.1:3000","http://localhost:3000");
//            }
//        };
//    }
//
//
///*
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//
//
//            registry.addMapping("/**")
//
//                    .allowedOrigins("http://127.0.0.1:3000","http://localhost:3000")
//
//
//                    .allowedMethods("*")
//
//                    .allowedHeaders("*")
//
//                    .allowCredentials(true);
//
//
//
//    //        registry.addMapping("/**")
//
//    //                .allowedOrigins("http://185.8.212.114", "http://e.archive.uz", "http://185.8.212.130")
//
//    //                .allowedMethods("*")
//
//    //                .allowedHeaders("*")
//
//    //                .allowCredentials(true);
//
//        }
//*/
//
//
//    @Override
//
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//
//
//        registry.addResourceHandler("/**")
//
//                .addResourceLocations("classpath:/static/")
//
////                .setCacheControl(CacheControl.maxAge(100, TimeUnit.DAYS))
//
//                .resourceChain(false)
//
//                .addResolver(new PushStateResourceResolver());
//
//    }
//
//
//    private class PushStateResourceResolver implements ResourceResolver {
//
//        private Resource index = new ClassPathResource("/static/index.html");
//
//        private List<String> handledExtensions = Arrays.asList("html","js", "json", "csv", "css", "png", "svg", "eot", "ttf", "otf", "woff", "appcache", "jpg", "jpeg", "gif", "ico");
//
//        private List<String> ignoredPaths = Arrays.asList("api");
//
//
//        @Override
//
//        public Resource resolveResource(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
//
//            return resolve(request, requestPath, locations);
//
//        }
//
//
//        @Override
//
//        public String resolveUrlPath(String resourcePath, List<? extends Resource> locations, ResourceResolverChain chain) {
//
//            Resource resolvedResource = resolve(null, resourcePath, locations);
//
//            if (resolvedResource == null) {
//
//                return null;
//
//            }
//
//            try {
//
//                return resolvedResource.getURL().toString();
//
//            } catch (IOException e) {
//
//                return resolvedResource.getFilename();
//
//            }
//
//        }
//
//
//        private Resource resolve(HttpServletRequest request, String requestPath, List<? extends Resource> locations) {
//
//            if (isIgnored(requestPath)) {
//
//                return null;
//
//            }
//
//
//            if (isHandled(requestPath)) {
//
//                try {
//
//                    return locations.stream()
//
//                            .map(loc -> createRelative(loc, requestPath))
//
//                            .filter(resource -> resource != null && resource.exists())
//
//                            .findFirst()
//
//                            .orElseGet(null);
//
//                } catch (Exception ignored) {
//
//                }
//
//            }
//
//
//            return index;
//
//        }
//
//
//        private Resource createRelative(Resource resource, String relativePath) {
//
//            try {
//
//                return resource.createRelative(relativePath);
//
//            } catch (IOException e) {
//
//                return null;
//
//            }
//
//        }
//
//
//
//        private boolean isIgnored(String path) {
//
//            return ignoredPaths.contains(path);
//
//        }
//
//
//        private boolean isHandled(String path) {
//
//            String extension = StringUtils.getFilenameExtension(path);
//
//            return handledExtensions.stream().anyMatch(ext -> ext.equals(extension));
//
//        }
//
//    }
//
//
//
//}
//
