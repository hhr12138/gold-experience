package basic.blog.goldexperience.factory;

import basic.blog.goldexperience.mapping.Mapping;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MappingFactory {
    private static class MappingFactoryInnerClass{
        private static MappingFactory mappingFactory = new MappingFactory();
    }
    @Resource(name="commonMapping")
    private Mapping commonMapping;
    public Mapping getMapping(){
        return commonMapping;
    }
    private MappingFactory(){
    }
    public static MappingFactory getFactory(){
        return MappingFactoryInnerClass.mappingFactory;
    }
}
