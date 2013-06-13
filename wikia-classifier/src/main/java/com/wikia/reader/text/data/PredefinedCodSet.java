package com.wikia.reader.text.data;/**
 * Author: Artur Dwornik
 * Date: 06.04.13
 * Time: 18:59
 */

import com.beust.jcommander.internal.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PredefinedCodSet {

    public static List<InstanceSource> getSet() {
        return set;
    }

    private static Logger logger = LoggerFactory.getLogger(PredefinedCodSet.class);
    private static final List<InstanceSource> set = new ArrayList<>();
    static {
        addSource("http://callofduty.wikia.com/", "G11/Attachments", Lists.newArrayList("index"));
        addSource("http://callofduty.wikia.com/", "Bravo Map Pack", Lists.newArrayList("dlc"));
        addSource("http://callofduty.wikia.com/", "Simian Justice", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "Trenches (World at War DS)", Lists.newArrayList("map"));
        addSource("http://callofduty.wikia.com/", "Tiger Camouflage", Lists.newArrayList("camouflage"));
        addSource("http://callofduty.wikia.com/", "Dispatch", Lists.newArrayList("map","level"));
        addSource("http://callofduty.wikia.com/", "Recon Drone", Lists.newArrayList("support","aircraft"));
        addSource("http://callofduty.wikia.com/", "Air Traffic Control", Lists.newArrayList("level","map"));
        addSource("http://callofduty.wikia.com/", "Lewis (Call of Duty)", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Juggernaut Recon", Lists.newArrayList("support"));
        addSource("http://callofduty.wikia.com/", "Elena Siegman", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Colossus Security Crew", Lists.newArrayList("organization"));
        addSource("http://callofduty.wikia.com/", "UGV", Lists.newArrayList("vehicle","ground vehicle"));
        addSource("http://callofduty.wikia.com/", "AK-74u", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "Stability", Lists.newArrayList("proficiency"));
        addSource("http://callofduty.wikia.com/", "Monkey See, Monkey Don't", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "Ballista", Lists.newArrayList("Ballista"));
        addSource("http://callofduty.wikia.com/", "Big Eye 6", Lists.newArrayList("aircraft"));
        addSource("http://callofduty.wikia.com/", "Warlord (character)", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Dust to Dust/Trivia", Lists.newArrayList("trivia"));
        addSource("http://callofduty.wikia.com/", "See My Vest", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "LSAT", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "You Have No Power Over Me", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "United States of America", Lists.newArrayList("location"));
        addSource("http://callofduty.wikia.com/", "HS-10/Camouflage", Lists.newArrayList("camouflage"));
        addSource("http://callofduty.wikia.com/", "IW engine", Lists.newArrayList("game engine"));
        addSource("http://callofduty.wikia.com/", "Who's Who", Lists.newArrayList("perk","achievement"));
        addSource("http://callofduty.wikia.com/", "Locke", Lists.newArrayList("disambiguation"));
        addSource("http://callofduty.wikia.com/", "Saunders", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "115 (song)", Lists.newArrayList("song","music"));
        addSource("http://callofduty.wikia.com/", "Rhine Crossing", Lists.newArrayList("level"));
        addSource("http://callofduty.wikia.com/", "Hutchinson", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Down and Dirty", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "Submachine Gun", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "Requiem", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "Prestige Shop", Lists.newArrayList("feature"));
        addSource("http://callofduty.wikia.com/", "Scrambler", Lists.newArrayList("disambiguation"));
        addSource("http://callofduty.wikia.com/", "EMPressive", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "Ballistic Knife/Camouflage", Lists.newArrayList("camouflage"));
        addSource("http://callofduty.wikia.com/", "The n00b", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Goldberg", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Not Today", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "Elite Member Camouflage", Lists.newArrayList("camouflage"));
        addSource("http://callofduty.wikia.com/", "The Oasis", Lists.newArrayList("map"));
        addSource("http://callofduty.wikia.com/", "Temple of Doom", Lists.newArrayList("map","level"));
        addSource("http://callofduty.wikia.com/", "Romanov", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "MH-6 Little Bird", Lists.newArrayList("support","vehicle","aircraft"));
        addSource("http://callofduty.wikia.com/", "Dead Air (Call of Duty: World at War)", Lists.newArrayList("achievement"));
        addSource("http://callofduty.wikia.com/", "No Russian/Trivia", Lists.newArrayList("trivia"));
        addSource("http://callofduty.wikia.com/", "Anatoly", Lists.newArrayList("character"));

        addSource("http://callofduty.wikia.com/", "Marcus_Burns", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Wallcroft", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Sandman", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Derek_\"Frost\"_Westbrook", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "Truck", Lists.newArrayList("character"));

        addSource("http://callofduty.wikia.com/", "Barrett_.50cal", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "M14", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "M4A1", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "AW-50", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "Smoke_Grenade", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "M1_Carbine", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "SMAW", Lists.newArrayList("weapon"));
    }

    private static void addSource(String url, String title, Collection<String> features) {
        try {
            set.add(new InstanceSource(new URL(url), title, features));
        } catch (MalformedURLException e) {
            logger.debug("Cannot parse predefined url.", e);
        }

    }
}
