class DeviceFactory {
    public static Device getDevice(String type) {
        if (type.equalsIgnoreCase("light")) {
            return new Light();
        } else if (type.equalsIgnoreCase("fan")) {
            return new Fan();
        } else if (type.equalsIgnoreCase("thermostat")) {
            return new Thermostat();
        }
        throw new IllegalArgumentException("Device not found");
    }
}

class Light implements Device {
    public void turnOn() { System.out.println("Light ON"); }
    public void turnOff() { System.out.println("Light OFF"); }
}

class Fan implements Device {
    public void turnOn() { System.out.println("Fan ON"); }
    public void turnOff() { System.out.println("Fan OFF"); }
}

class Thermostat implements Device {
    public void turnOn() { System.out.println("Thermostat working"); }
    public void turnOff() { System.out.println("Thermostat OFF"); }
}
class HomeConfig {
    String owner;
    int rooms;
    boolean security;

    private HomeConfig(Builder b) {
        this.owner = b.owner;
        this.rooms = b.rooms;
        this.security = b.security;
    }

    static class Builder {
        String owner;
        int rooms;
        boolean security;

        Builder setOwner(String o) { this.owner = o; return this; }
        Builder setRooms(int r) { this.rooms = r; return this; }
        Builder setSecurity(boolean s) { this.security = s; return this; }
        HomeConfig build() { return new HomeConfig(this); }
    }

    public String toString() {
        return "HomeConfig[owner=" + owner + ", rooms=" + rooms + ", security=" + security + "]";
    }
}

class SmartTV {
    void on() { System.out.println("TV ON"); }
    void off() { System.out.println("TV OFF"); }
}

class TVAdapter implements Device {
    SmartTV tv = new SmartTV();
    public void turnOn() { tv.on(); }
    public void turnOff() { tv.off(); }
}

class DeviceWrapper implements Device {
    Device d;
    DeviceWrapper(Device d) { this.d = d; }
    public void turnOn() { d.turnOn(); }
    public void turnOff() { d.turnOff(); }
}

class LoggedDevice extends DeviceWrapper {
    LoggedDevice(Device d) { super(d); }
    public void turnOn() {
        System.out.println("Log: turning ON");
        super.turnOn();
    }
    public void turnOff() {
        System.out.println("Log: turning OFF");
        super.turnOff();
    }
}

class MotionSensor {
    Observer obs;
    void setObserver(Observer o) { this.obs = o; }
    void detect() {
        if (obs != null) obs.notify("Movement found");
    }
}

class Aggressive implements EnergyPlan {
    public void run() { System.out.println("Aggressive plan: turn off fast"); }
}

class Balanced implements EnergyPlan {
    public void run() { System.out.println("Balanced plan: save + comfort"); }
}

class EnergyManager {
    EnergyPlan plan;
    void setPlan(EnergyPlan p) { this.plan = p; }
    void apply() { plan.run(); }
}

public class SmartHomeDemo {
    public static void main(String[] args) {
     
        HomeConfig cfg = new HomeConfig.Builder()
                .setOwner("Akash")
                .setRooms(3)
                .setSecurity(true)
                .build();
        System.out.println(cfg);

        Device light = DeviceFactory.getDevice("light");
        Device fan = DeviceFactory.getDevice("fan");

        Device tv = new TVAdapter();

        Device logFan = new LoggedDevice(fan);

        light.turnOn();
        logFan.turnOn();
        tv.turnOn();

        MotionSensor sensor = new MotionSensor();
        sensor.setObserver(msg -> {
            System.out.println("System says: " + msg);
            light.turnOn();
        });
        sensor.detect();
        EnergyManager em = new EnergyManager();
        em.setPlan(new Balanced());
        em.apply();
    }
}
