package drobczyk.bartlomiej.model.dto.apis.weather;

public class WeatherDto {
    private double temperature;
    private double feelsLikeTemperature;
    private double windSpeed;
    private String description;
    private String icon;
    private String sunrise;
    private String sunset;

    public WeatherDto(double temperature, double feelsLikeTemperature, double windSpeed, String description,
                      String icon, String sunrise, String sunset) {
        this.temperature = temperature;
        this.feelsLikeTemperature = feelsLikeTemperature;
        this.windSpeed = windSpeed;
        this.description = description;
        this.icon = icon;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLikeTemperature() {
        return feelsLikeTemperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }
}
