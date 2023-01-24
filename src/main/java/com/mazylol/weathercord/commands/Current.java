package com.mazylol.weathercord.commands;

import com.mazylol.weathercord.call.CurrentWeather;
import com.mazylol.weathercord.models.current.CurrentRoot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;
import java.util.Objects;

public class Current extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("current")) {
            String location = Objects.requireNonNull(event.getOption("location")).getAsString().replaceAll("\\s", "");
            String unit = "metric";

            if (event.getOption("units") != null ) {
                unit = Objects.requireNonNull(event.getOption("units")).getAsString();
            }

            try {
                CurrentRoot weather = CurrentWeather.Get(location, unit);

                String abbv = switch (unit) {
                    case "standard" -> " K";
                    case "imperial" -> " F";
                    default -> " C";
                };

                EmbedBuilder eb = new EmbedBuilder();

                eb.setThumbnail("https://openweathermap.org/img/w/" + weather.weather.get(0).icon + ".png");
                eb.setColor(0x00AE86);
                eb.setTimestamp(Instant.now());

                eb.setTitle("Current Weather for " + weather.name);
                eb.setDescription(weather.weather.get(0).description);
                eb.addField("Temperature", weather.main.temp + abbv, true);
                eb.addField("High", weather.main.temp_max + abbv, true);
                eb.addField("Low", weather.main.temp_min + abbv, true);
                eb.addField("Humidity", weather.main.humidity + "%", true);
                eb.addField("Pressure", weather.main.pressure + " hPa", true);
                eb.addField("Wind Speed", weather.wind.speed + " m/s", true);

                event.replyEmbeds(eb.build()).setEphemeral(true).queue();
            } catch (IOException | InterruptedException e) {
                event.reply("Something went wrong!").queue();
                throw new RuntimeException(e);
            }
        }
    }
}
