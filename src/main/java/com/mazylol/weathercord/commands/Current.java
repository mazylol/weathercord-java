package com.mazylol.weathercord.commands;

import com.mazylol.weathercord.call.CurrentWeather;
import com.mazylol.weathercord.models.current.CurrentRoot;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
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

                event.reply(String.valueOf(weather.main.temp)).queue();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
