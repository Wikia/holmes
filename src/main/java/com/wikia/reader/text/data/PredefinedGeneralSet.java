package com.wikia.reader.text.data;/**
 * Author: Artur Dwornik
 * Date: 08.04.13
 * Time: 23:21
 */

import com.beust.jcommander.internal.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PredefinedGeneralSet {
    private static Logger logger = LoggerFactory.getLogger(PredefinedGeneralSet.class);

    public static List<InstanceSource> getSet() {
        return set;
    }

    private static final List<InstanceSource> set = new ArrayList<>();
    static {
        addSource("http://mlp-fim-fan-series.wikia.com/", "Sir_Vandias_Abyss", Lists.newArrayList("character"));
        addSource("http://mugen.wikia.com/", "Abubo_Rao", Lists.newArrayList("character", "boss"));
        addSource("http://thelastofus.wikia.com/", "Boston", Lists.newArrayList("location"));
        addSource("http://thelastofus.wikia.com/", "Shotgun", Lists.newArrayList("weapon"));
        addSource("http://ben-10-aliens.wikia.com/", "Gravattack", Lists.newArrayList("character"));
        addSource("http://fallout.wikia.com/", "Hull,_12_gauge", Lists.newArrayList("item"));
        addSource("http://fallout.wikia.com/", "Table_leg", Lists.newArrayList("item"));
        addSource("http://fallout.wikia.com/", "Reynolds", Lists.newArrayList("character"));
        addSource("http://fallout.wikia.com/", "Fresh_carrot_(Fallout:_New_Vegas)", Lists.newArrayList("item"));
        addSource("http://fallout.wikia.com/", "Jing_Tie_Gahng", Lists.newArrayList("character"));
        addSource("http://fallout.wikia.com/", "Dragon", Lists.newArrayList("character"));
        addSource("http://fallout.wikia.com/", "Myron", Lists.newArrayList("character"));
        addSource("http://fallout.wikia.com/", "Arefu", Lists.newArrayList("location"));
        addSource("http://fallout.wikia.com/", "Big_Town", Lists.newArrayList("location"));
        addSource("http://fallout.wikia.com/", "Canterbury_Commons", Lists.newArrayList("location"));

        addSource("http://marvel.wikia.com/", "Hulk_(Robert_Bruce_Banner)", Lists.newArrayList("character"));
        addSource("http://marvel.wikia.com/", "Wolverine_(James_\"Logan\"_Howlett)", Lists.newArrayList("character"));
        addSource("http://marvel.wikia.com/", "Hulk_(Robert_Bruce_Banner)", Lists.newArrayList("character"));

        addSource("http://how-i-met-your-mother.wikia.com/", "Robin_Scherbatsky", Lists.newArrayList("character"));
        addSource("http://how-i-met-your-mother.wikia.com/", "Double_Date", Lists.newArrayList("tv_episode"));

        addSource("http://onelifetolive.wikia.com/", "Sam_Manning", Lists.newArrayList("character"));

        addSource("http://totalannihilation.wikia.com/", "Mana_Refinery_(TAK_Unit_Creon)", Lists.newArrayList("unit"));
        addSource("http://texmurphy.wikia.com/", "Sylvia_Linsky", Lists.newArrayList("character"));
        addSource("http://x-files.wikia.com/", "Fox_Mulder", Lists.newArrayList("character"));
        addSource("http://aceattorney.wikia.com/", "Phoenix_Wright", Lists.newArrayList("character"));
        addSource("http://madagascar.wikia.com/", "Mort", Lists.newArrayList("character"));
        addSource("http://friends.wikia.com/", "Ross_Geller", Lists.newArrayList("character"));

        addSource("http://borderlands.wikia.com/", "Gaige", Lists.newArrayList("character"));
        addSource("http://borderlands.wikia.com/", "Get_A_Little_Blood_on_the_Tires_(achievement)", Lists.newArrayList("achievement"));
        addSource("http://borderlands.wikia.com/", "Arid_Badlands", Lists.newArrayList("location"));

        addSource("http://yami.wikia.com/", "Ookii_Aka_Hokoatama", Lists.newArrayList("character"));
        addSource("http://yami.wikia.com/", "Sunset", Lists.newArrayList("book_series"));
        addSource("http://yami.wikia.com/", "\"Him\"_(History_of_Topazo)", Lists.newArrayList("character"));

        addSource("http://grandia.wikia.com/", "Justin", Lists.newArrayList("character"));
        addSource("http://grandia.wikia.com/", "Dight", Lists.newArrayList("location"));


        addSource("http://maplestory.wikia.com/", "Military_Camp_3", Lists.newArrayList("location", "map"));
        addSource("http://maplestory.wikia.com/", "Quests/58JP/Adaptation_3", Lists.newArrayList("quest", "mission"));


        addSource("http://fanfictiondisney.wikia.com/", "Flik", Lists.newArrayList("character"));
        addSource("http://fanfictiondisney.wikia.com/", "Joe_Kido", Lists.newArrayList("character"));


        addSource("http://lego-space.wikia.com/", "Alienator", Lists.newArrayList("vehicle"));

        addSource("http://x-files.wikia.com/", "TXF_Season_8", Lists.newArrayList("tv_season"));
        addSource("http://x-files.wikia.com/", "Roadrunners", Lists.newArrayList("tv_episode"));
        addSource("http://x-files.wikia.com/", "Existence", Lists.newArrayList("tv_episode"));


        addSource("http://how-i-met-your-mother.wikia.com/", "Season_8", Lists.newArrayList("tv_season"));
        addSource("http://how-i-met-your-mother.wikia.com/", "The_Mother", Lists.newArrayList("character"));
        addSource("http://how-i-met-your-mother.wikia.com/", "Barney_Stinson", Lists.newArrayList("character"));
        addSource("http://how-i-met-your-mother.wikia.com/", "The_Broath", Lists.newArrayList("tv_episode"));
        addSource("http://how-i-met-your-mother.wikia.com/", "46_Minutes", Lists.newArrayList("tv_episode"));


        addSource("http://southpark.wikia.com/", "The_Last_of_the_Meheecans", Lists.newArrayList("tv_episode"));
        addSource("http://southpark.wikia.com/", "Eric_Cartman", Lists.newArrayList("character"));

        addSource("http://simpsons.wikia.com/", "Season_11", Lists.newArrayList("tv_season"));
        // addSource("http://simpsons.wikia.com/", "Days_of_Wine_and_D%27oh%27ses", Lists.newArrayList("tv_episode"));
        addSource("http://simpsons.wikia.com/", "Saddlesore_Galactica", Lists.newArrayList("tv_episode"));

        set.addAll(PredefinedCodSet.getSet());
    }

    private static void addSource(String url, String title, Collection<String> features) {
        try {
            set.add(new InstanceSource(new URL(url), title, features));
        } catch (MalformedURLException e) {
            logger.debug("Cannot parse predefined url.", e);
        }

    }
}
