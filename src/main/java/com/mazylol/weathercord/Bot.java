package com.mazylol.weathercord;

import com.mazylol.weathercord.commands.Current;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

public class Bot {
    public static String weatherApiKey;

    public static void main(String[] args) throws Exception {
        Dotenv dotenv = Dotenv.load();

        weatherApiKey = dotenv.get("WEATHER_API_KEY");

        JDA jda = JDABuilder.createDefault(dotenv.get("DEVTOKEN"))
                .setActivity(Activity.playing("no storms?"))
                .addEventListeners(new Current())
                .build().awaitReady();

        Guild guild = jda.getGuildById(dotenv.get("GUILD_ID"));

        assert guild != null;
        guild.updateCommands().addCommands(
                Commands.slash("current", "current weather")
                        .addOptions(
                                new OptionData(OptionType.STRING, "location", "town, city, address, etc", true),
                                new OptionData(OptionType.STRING, "units", "units of measure", false)
                                        .addChoice("metric", "metric")
                                        .addChoice("kelvin", "standard")
                                        .addChoice("imperial", "imperial")
                        )
        ).queue();
    }
}