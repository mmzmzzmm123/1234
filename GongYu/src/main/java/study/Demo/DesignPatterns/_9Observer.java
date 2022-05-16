package study.Demo.DesignPatterns;

import java.util.ArrayList;
import java.util.List;

class WeatherStation {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();
        CurrentConditionsDisplay currentConditionsDisplay = new CurrentConditionsDisplay(weatherData);
        StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);

        weatherData.setMeasurements(0, 0, 0);
        weatherData.setMeasurements(1, 1, 1);
    }
}
// ------------------------------------------------
interface _9Observer {
    void update(float temp, float humidity, float pressure);
}

class StatisticsDisplay implements _9Observer {
    public StatisticsDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }
    @Override
    public void update(float temp, float humidity, float pressure) {
        System.out.println("StatisticsDisplay.update: " + temp + " " + humidity + " " + pressure);
    }
}

class CurrentConditionsDisplay implements _9Observer {
    public CurrentConditionsDisplay(Subject weatherData) {
        weatherData.registerObserver(this);
    }
    @Override
    public void update(float temp, float humidity, float pressure) {
        System.out.println("CurrentConditionsDisplay.update: " + temp + " " + humidity + " " + pressure);
    }
}
// ---------------------------------------------------------------
interface Subject {
    void registerObserver(_9Observer o);
    void removeObserver(_9Observer o);
    void notifyObserver();
}

class WeatherData implements Subject {
    private List<_9Observer> observers;
    private float temperature;
    private float humidity;
    private float pressure;
    public WeatherData() {
        observers = new ArrayList<>();
    }
    public void setMeasurements(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        notifyObserver();
    }

    @Override
    public void registerObserver(_9Observer o) {
        observers.add(o);
    }
    @Override
    public void removeObserver(_9Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }
    @Override
    public void notifyObserver() {
        for (_9Observer o : observers) {
            o.update(temperature, humidity, pressure);
        }
    }
}




