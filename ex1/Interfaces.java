interface Device {
    void turnOn();
    void turnOff();
}

interface Observer {
    void notify(String msg);
}

interface EnergyPlan {
    void run();
}
