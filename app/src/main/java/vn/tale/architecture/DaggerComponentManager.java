package vn.tale.architecture;

import android.support.v4.util.ArrayMap;
import java.util.Map;

/**
 * Created by Giang Nguyen on 3/9/17.
 */

public class DaggerComponentManager {

  private final ComponentFactory componentFactory;
  private final Map<String, Object> componentMap;

  public DaggerComponentManager(ComponentFactory componentFactory) {
    this.componentFactory = componentFactory;
    this.componentMap = new ArrayMap<>();
  }

  public void makeComponent(Class<?> componentClass, Object module) {
    final Object component = componentFactory.make(module);
    if (component == null) {
      throw new DaggerComponentException("Can't create component "
          + componentClass.getName()
          + " with given module "
          + module.getClass().getName());
    }
    componentMap.put(componentClass.getName(), component);
  }

  @SuppressWarnings("unchecked") public <T> T getComponent(Class<T> componentClass) {
    final Object component = componentMap.get(componentClass.getName());
    if (component == null) {
      throw new DaggerComponentException("Component "
          + componentClass.getName()
          + " not found. Please call "
          + DaggerComponentManager.class.getName()
          + ".make() first");
    }
    return (T) component;
  }

  public boolean hasComponent(Class<?> componentClass) {
    return componentMap.containsKey(componentClass.getName());
  }

  public void destroyComponent(Class<?> componentClass) {
    componentMap.remove(componentClass.getName());
  }

  public interface ComponentFactory {
    Object make(Object module);
  }

  public class DaggerComponentException extends RuntimeException {
    public DaggerComponentException(String message) {
      super(message);
    }
  }
}
