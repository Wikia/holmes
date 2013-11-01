package com.wikia.classifier.text.data;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.wikia.api.model.PageInfo;
import com.wikia.api.service.PageService;
import com.wikia.api.service.PageServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
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
        //addSource("http://maplestory.wikia.com/", "Quests/58JP/Adaptation_3", Lists.newArrayList("quest", "mission"));


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

        addSource("http://once.wikia.com/", "Emma_Swan", Lists.newArrayList("character"));
        addSource("http://once.wikia.com/", "1x01_Pilot", Lists.newArrayList("tv_episode"));
        addSource("http://once.wikia.com/", "2x01_Broken", Lists.newArrayList("tv_episode"));
        addSource("http://once.wikia.com/", "Season_two", Lists.newArrayList("tv_season"));
        addSource("http://once.wikia.com/", "2x08_Into_the_Deep", Lists.newArrayList("tv_episode"));
        addSource("http://once.wikia.com/", "2x07_Child_of_the_Moon", Lists.newArrayList("tv_episode"));

        addSource("http://fallout.wikia.com/", "Find_the_excavator_chip", Lists.newArrayList("quest"));
        addSource("http://fallout.wikia.com/", "Clear_Wanamingo_Mine", Lists.newArrayList("quest"));
        addSource("http://fallout.wikia.com/", "Motion_sensor", Lists.newArrayList("item"));
        addSource("http://fallout.wikia.com/", "Redding", Lists.newArrayList("location"));
        addSource("http://fallout.wikia.com/", "Mordino", Lists.newArrayList("organization"));
        addSource("http://fallout.wikia.com/", "Bobblehead_-_Energy_Weapons", Lists.newArrayList("item","bobblehead"));
        addSource("http://fallout.wikia.com/", "Picking_up_the_Trail", Lists.newArrayList("achievement"));
        addSource("http://fallout.wikia.com/", "Take_it_Back!", Lists.newArrayList("achievement"));
        addSource("http://fallout.wikia.com/", "Fallout_3", Lists.newArrayList("game"));


        addSource("http://laststory.wikia.com/", "The_Last_Story", Lists.newArrayList("game", "genre:rpg"));
        addSource("http://laststory.wikia.com/", "Lazulis_City", Lists.newArrayList("location", "genre:rpg"));
        addSource("http://laststory.wikia.com/", "Lightning_Axe", Lists.newArrayList("weapon", "genre:rpg"));
        addSource("http://laststory.wikia.com/", "Vono_Islands", Lists.newArrayList("location", "genre:rpg"));

        addSource("http://medalofhonor.wikia.com/", "DD_M4V1", Lists.newArrayList("weapon"));
        addSource("http://medalofhonor.wikia.com/", "AK-103", Lists.newArrayList("weapon"));
        addSource("http://medalofhonor.wikia.com/", "AH-64_Apache", Lists.newArrayList("vehicle", "aircraft"));
        addSource("http://medalofhonor.wikia.com/", "A-10_Thunderbolt_II", Lists.newArrayList("vehicle", "aircraft"));
        addSource("http://medalofhonor.wikia.com/", "MH-47_Chinook", Lists.newArrayList("vehicle", "aircraft"));

        addSource("http://thief.wikia.com/", "Broadhead_Arrow", Lists.newArrayList("ammo", "arrow"));
        addSource("http://thief.wikia.com/", "Flash_Bomb", Lists.newArrayList("weapon","explosive"));
        addSource("http://thief.wikia.com/", "Noisemaker_Arrow", Lists.newArrayList("ammo", "arrow"));
        addSource("http://thief.wikia.com/", "Sword", Lists.newArrayList("weapon"));
        addSource("http://thief.wikia.com/", "Bow", Lists.newArrayList("weapon"));

        addSource("http://stalker.wikia.com/", "Dead_City", Lists.newArrayList("location"));
        addSource("http://stalker.wikia.com/", "Mercenaries", Lists.newArrayList("organization"));

        addSource("http://rage.wikia.com/", "Jackal_Canyon", Lists.newArrayList("location"));
        addSource("http://rage.wikia.com/", "Capital_Prime", Lists.newArrayList("location"));
        addSource("http://rage.wikia.com/", "Debunked", Lists.newArrayList("achievement"));
        addSource("http://rage.wikia.com/", "Combat_Shotgun", Lists.newArrayList("weapon"));
        addSource("http://rage.wikia.com/", "Sniper_Rifle", Lists.newArrayList("weapon"));

        addSource("http://rainbowsix.wikia.com/", "Scout_Tactical", Lists.newArrayList("weapon"));
        addSource("http://rainbowsix.wikia.com/", "P99", Lists.newArrayList("weapon"));
        addSource("http://rainbowsix.wikia.com/", "FNC", Lists.newArrayList("weapon"));

        addSource("http://weeds.wikia.com/", "Season_4", Lists.newArrayList("tv_season"));
        addSource("http://weeds.wikia.com/", "Season_6", Lists.newArrayList("tv_season"));
        addSource("http://weeds.wikia.com/", "Celia_Hodes", Lists.newArrayList("characters"));

        addSource("http://thefollowing.wikia.com/", "Season_1", Lists.newArrayList("tv_season"));
        addSource("http://thefollowing.wikia.com/", "Jeananne_Goossen", Lists.newArrayList("person"));
        addSource("http://thefollowing.wikia.com/", "Jennifer_Mason", Lists.newArrayList("character"));

        addSource("http://movies.wikia.com/", "Oscar", Lists.newArrayList("real:reward"));
        addSource("http://fallout.wikia.com/", "Swampfolk", Lists.newArrayList("creature","unit"));
        addSource("http://fallout.wikia.com/", "Coyote", Lists.newArrayList("creature","unit"));
        addSource("http://fallout.wikia.com/", "Radscorpion", Lists.newArrayList("creature","unit"));

        addSource("http://simcity.wikia.com/", "SimCity_3000", Lists.newArrayList("game"));
        addSource("http://civilization.wikia.com/", "Missionary_(Civ5)", Lists.newArrayList("unit"));
        addSource("http://civilization.wikia.com/", "Cathedral_(Civ5)", Lists.newArrayList("building"));
        addSource("http://civilization.wikia.com/", "Stock_exchange_(Civ5)", Lists.newArrayList("building"));
        addSource("http://civilization.wikia.com/", "Civilization_V", Lists.newArrayList("game"));
        addSource("http://starcraft.wikia.com/", "StarCraft:_Brood_War", Lists.newArrayList("game"));
        addSource("http://starcraft.wikia.com/", "Guardian", Lists.newArrayList("unit", "creature"));
        addSource("http://callofduty.wikia.com/", "Call_of_Duty:_Modern_Warfare:_Reflex_Edition", Lists.newArrayList("game"));
        addSource("http://callofduty.wikia.com/", "Invasion_Map_Pack", Lists.newArrayList("dlc"));
        addSource("http://fallout.wikia.com/", "The_Pitt_(add-on)", Lists.newArrayList("dlc"));

        addSource("http://fallout.wikia.com/", "Brotherhood_of_Steel", Lists.newArrayList("organization"));

        addSource("http://movies.wikia.com/", "American_Film_Institute", Lists.newArrayList("organization"));

        addSource("http://starwars.wikia.com", "Star_Wars:_Clone_Wars:_Volume_One", Lists.newArrayList("tv_series"));
        addSource("http://starwars.wikia.com", "Droid_Revolution", Lists.newArrayList("event"));
        addSource("http://starwars.wikia.com", "Death_Star_Designer", Lists.newArrayList("game"));
        addSource("http://harrypotter.wikia.com", "Harry_Potter_and_the_Philosopher's_Stone_(soundtrack)", Lists.newArrayList("soundtrack"));
        addSource("http://harrypotter.wikia.com", "Harry_Potter_and_the_Prisoner_of_Azkaban_(soundtrack)", Lists.newArrayList("soundtrack"));
        addSource("http://harrypotter.wikia.com", "Harry_Potter_and_the_Goblet_of_Fire_(soundtrack)", Lists.newArrayList("soundtrack"));
        addSource("http://harrypotter.wikia.com", "Harry_Potter_and_the_Prisoner_of_Azkaban_(film)", Lists.newArrayList("movie"));
        addSource("http://harrypotter.wikia.com", "Harry_Potter_and_the_Chamber_of_Secrets_(film)", Lists.newArrayList("movie"));
        addSource("http://harrypotter.wikia.com", "Harry_Potter:_The_Exhibition", Lists.newArrayList("exhibition"));
        addSource("http://harrypotter.wikia.com", "Harry_Potter:_Find_Scabbers", Lists.newArrayList("game"));
        addSource("http://harrypotter.wikia.com", "Pottermore", Lists.newArrayList("website"));
        addSource("http://24.wikia.com", "North_Hollywood", Lists.newArrayList("location"));
        addSource("http://24.wikia.com", "Mercerwood", Lists.newArrayList("location"));
        addSource("http://24.wikia.com", "Ontario_Airport", Lists.newArrayList("location"));
        addSource("http://24.wikia.com", "24", Lists.newArrayList("tv_series"));
        addSource("http://24.wikia.com", "Jack_Bauer", Lists.newArrayList("character"));
        addSource("http://lookout.wikia.com", "The_Last_Warrior", Lists.newArrayList("book_series"));
        addSource("http://lookout.wikia.com", "Dragon_Ball_Z:_The_Tree_of_Might", Lists.newArrayList("movie"));
        addSource("http://chuck-nbc.wikia.com", "Chuck_(Television_Series)", Lists.newArrayList("tv_series"));
        addSource("http://chuck-nbc.wikia.com", "Chuck", Lists.newArrayList("character"));
        addSource("http://chuck-nbc.wikia.com", "Eleanor_Woodcomb", Lists.newArrayList("character"));
        addSource("http://chuck-nbc.wikia.com", "Episode_407:_Chuck_Versus_the_First_Fight", Lists.newArrayList("tv_episode"));
        addSource("http://alf.wikia.com", "ALF_(TV_series)", Lists.newArrayList("tv_series"));
        addSource("http://alf.wikia.com", "Keith", Lists.newArrayList("character"));
        addSource("http://alf.wikia.com", "Albert", Lists.newArrayList("character"));
        addSource("http://addamsfamily.wikia.com", "Gomez_Addams", Lists.newArrayList("character"));
        addSource("http://addamsfamily.wikia.com", "The_Addams_Family_(1992)", Lists.newArrayList("tv_series"));
        addSource("http://addamsfamily.wikia.com", "Fester", Lists.newArrayList("character"));
        addSource("http://addamsfamily.wikia.com", "The_Addams_Family_(TV_Series)", Lists.newArrayList("tv_series"));
        addSource("http://addamsfamily.wikia.com", "The_Addams_Family_XXX", Lists.newArrayList("movie"));
        addSource("http://addamsfamily.wikia.com", "The_Addams_Family_(1991)", Lists.newArrayList("movie"));
        addSource("http://x-files.wikia.com", "The_X-Files", Lists.newArrayList("tv_series"));
        addSource("http://x-files.wikia.com", "Joey_Shea", Lists.newArrayList("person"));
        addSource("http://x-files.wikia.com", "Deep_Throat", Lists.newArrayList("character"));
        addSource("http://x-files.wikia.com", "Alex_Krycek", Lists.newArrayList("character"));
        addSource("http://avatar.wikia.com", "Avatar:_The_Last_Airbender", Lists.newArrayList("tv_series"));
        addSource("http://avatar.wikia.com", "The_Legend_of_Korra", Lists.newArrayList("tv_series"));
        addSource("http://avatar.wikia.com", "Zuko", Lists.newArrayList("character"));
        addSource("http://avatar.wikia.com", "Iroh", Lists.newArrayList("character"));
        addSource("http://avatar.wikia.com", "Film:The_Last_Airbender", Lists.newArrayList("movie"));
        addSource("http://cowboybebop.wikia.com", "Cowboy_Bebop", Lists.newArrayList("tv_series"));
        addSource("http://cowboybebop.wikia.com", "Cowboy_Bebop:_Knockin%27_on_Heaven%27s_Door", Lists.newArrayList("movie"));
        addSource("http://cowboybebop.wikia.com", "Spike_Spiegel", Lists.newArrayList("character"));
        addSource("http://cowboybebop.wikia.com", "Vicious", Lists.newArrayList("character"));
        addSource("http://cowboybebop.wikia.com", "Asteroid_Blues", Lists.newArrayList("tv_episode"));
        addSource("http://trueblood.wikia.com", "True_Blood", Lists.newArrayList("tv_series"));
        addSource("http://trueblood.wikia.com", "Cold_Grey_Light_of_Dawn", Lists.newArrayList("tv_episode"));
        addSource("http://godfather.wikia.com", "Vito_Corleone", Lists.newArrayList("character"));
        addSource("http://powerrangers.wikia.com", "Power_Rangers", Lists.newArrayList("tv_series"));
        addSource("http://powerrangers.wikia.com", "Category:Blue_Ranger", Lists.newArrayList("character"));
        addSource("http://powerrangers.wikia.com", "Billy_Cranston", Lists.newArrayList("character"));
        addSource("http://powerrangers.wikia.com", "Trini_Kwan", Lists.newArrayList("person"));
        addSource("http://powerrangers.wikia.com", "Super_Sentai", Lists.newArrayList("tv_series"));
        addSource("http://bleach.wikia.com", "Genryūsai Shigekuni Yamamoto vs. Yhwach", Lists.newArrayList("other"));
        addSource("http://tardis.wikia.com", "The_Name_of_the_Doctor_(TV_story)", Lists.newArrayList("tv_episode"));
        addSource("http://tardis.wikia.com", "River_Song", Lists.newArrayList("character"));
        addSource("http://tardis.wikia.com", "Simon_Furman", Lists.newArrayList("person"));
        addSource("http://lotro.wikia.com", "Angmar", Lists.newArrayList("location"));
        addSource("http://sonic.wikia.com", "Cree_Summer", Lists.newArrayList("person"));
        addSource("http://sonic.wikia.com", "Marc_Thompson", Lists.newArrayList("person"));
        addSource("http://india.wikia.com", "Devulapalli_Krishnasastri", Lists.newArrayList("person"));
        addSource("http://music.wikia.com", "Reanimation", Lists.newArrayList("music_album"));
        addSource("http://music.wikia.com", "T.A.T.u.", Lists.newArrayList("music_band"));
        addSource("http://bleach.wikia.com", "Ichigo_Kurosaki_vs._Grimmjow_Jaegerjaquez:_Round_Two", Lists.newArrayList("tv_episode_event"));
        addSource("http://bleach.wikia.com", "3rd_Division_vs._The_Hollows", Lists.newArrayList("tv_episode_event"));
        addSource("http://bleach.wikia.com", "The_Arrival%3A_The_Visored_Appear", Lists.newArrayList("tv_episode_event"));
        addSource("http://bleach.wikia.com", "Invading_Army_Arc,_Final_Conclusion!", Lists.newArrayList("tv_episode"));
        addSource("http://bleach.wikia.com", "Hakuda", Lists.newArrayList("other"));
        addSource("http://itlaw.wikia.com", "MP3", Lists.newArrayList("other"));
        addSource("http://itlaw.wikia.com", "PTO_Guidelines:_Computer_Programs_and_Mathematical_Algorithms", Lists.newArrayList("other"));

        // tv series
        addSource("http://inuyasha.wikia.com", "InuYasha_Series", Lists.newArrayList("tv_series"));
        addSource("http://futurama.wikia.com", "Futurama", Lists.newArrayList("tv_series"));
        addSource("http://lost.wikia.com", "LOST", Lists.newArrayList("tv_series"));
        addSource("http://seinfeld.wikia.com", "Seinfeld", Lists.newArrayList("tv_series"));
        addSource("http://charmed.wikia.com", "Charmed_(TV_series)", Lists.newArrayList("tv_series"));
        addSource("http://smallville.wikia.com", "Smallville_(TV_series)", Lists.newArrayList("tv_series"));
        addSource("http://naruto.wikia.com", "Naruto_(series)", Lists.newArrayList("tv_series"));
        addSource("http://heroes.wikia.com", "Heroes", Lists.newArrayList("tv_series"));
        addSource("http://scrubs.wikia.com", "Scrubs", Lists.newArrayList("tv_series"));
        addSource("http://ncis.wikia.com", "NCIS_(series)", Lists.newArrayList("tv_series"));
        addSource("http://macgyver.wikia.com", "MacGyver", Lists.newArrayList("tv_series"));
        addSource("http://chuck-nbc.wikia.com", "Chuck_(Television_Series)", Lists.newArrayList("tv_series"));
        addSource("http://bleach.wikia.com", "Bleach", Lists.newArrayList("tv_series"));
        addSource("http://bones.wikia.com", "Bones_(TV_series)", Lists.newArrayList("tv_series"));
        addSource("http://90210.wikia.com", "90210", Lists.newArrayList("tv_series"));
        addSource("http://fringe.wikia.com", "Fringe", Lists.newArrayList("tv_series"));
        addSource("http://cheers.wikia.com", "Cheers_(TV_series)", Lists.newArrayList("tv_series"));
        addSource("http://merlin.wikia.com", "Merlin_(TV_Series)", Lists.newArrayList("tv_series"));
        addSource("http://lostpedia.wikia.com", "Lost", Lists.newArrayList("tv_series"));
        addSource("http://glee.wikia.com", "Glee", Lists.newArrayList("tv_series"));
        addSource("http://castle.wikia.com", "Castle", Lists.newArrayList("tv_series"));
        addSource("http://girls.wikia.com", "Girls", Lists.newArrayList("tv_series"));
        addSource("http://southland.wikia.com", "Southland", Lists.newArrayList("tv_series"));
        addSource("http://community-sitcom.wikia.com", "Community", Lists.newArrayList("tv_series"));
        addSource("http://spartacus.wikia.com", "Spartacus:_Blood_and_Sand", Lists.newArrayList("tv_series"));
        addSource("http://justified.wikia.com", "Justified", Lists.newArrayList("tv_series"));
        addSource("http://victorious.wikia.com", "Victorious", Lists.newArrayList("tv_series"));
        addSource("http://treme.wikia.com", "Treme", Lists.newArrayList("tv_series"));
        addSource("http://grimm.wikia.com", "Grimm_(TV_Series)", Lists.newArrayList("tv_series"));
        addSource("http://greendale.wikia.com", "Community", Lists.newArrayList("tv_series"));
        addSource("http://nikita2010.wikia.com", "Nikita_(TV)", Lists.newArrayList("tv_series"));
        addSource("http://arrow.wikia.com", "Arrow", Lists.newArrayList("tv_series"));
        addSource("http://awkward.wikia.com", "Awkward", Lists.newArrayList("tv_series"));
        addSource("http://luck.wikia.com", "Luck", Lists.newArrayList("tv_series"));
        addSource("http://suits.wikia.com", "Suits", Lists.newArrayList("tv_series"));
        addSource("http://scandal.wikia.com", "Scandal", Lists.newArrayList("tv_series"));
        addSource("http://defiance.wikia.com", "Defiance_(TV)", Lists.newArrayList("tv_series"));
        addSource("http://revengeabc.wikia.com", "Revenge", Lists.newArrayList("tv_series"));
        addSource("http://suburgatory.wikia.com", "Suburgatory", Lists.newArrayList("tv_series"));
        addSource("http://unforgettable.wikia.com", "Unforgettable", Lists.newArrayList("tv_series"));
        addSource("http://hannibal.wikia.com", "Hannibal_(TV_series)", Lists.newArrayList("tv_series"));
        addSource("http://smash.wikia.com", "Smash", Lists.newArrayList("tv_series"));
        addSource("http://www.boss.wikia.com", "Boss", Lists.newArrayList("tv_series"));
        addSource("http://elementary.wikia.com", "Elementary", Lists.newArrayList("tv_series"));
        addSource("http://louisck.wikia.com", "Louie", Lists.newArrayList("tv_series"));
        addSource("http://touchpedia.wikia.com", "Touch", Lists.newArrayList("tv_series"));
        addSource("http://bunheads.wikia.com", "Bunheads", Lists.newArrayList("tv_series"));
        addSource("http://schneidersbakery.wikia.com", "Victorious", Lists.newArrayList("tv_series"));
        addSource("http://veep.wikia.com", "Veep", Lists.newArrayList("tv_series"));
        addSource("http://banshee.wikia.com", "Banshee", Lists.newArrayList("tv_series"));
        addSource("http://graceland.wikia.com", "Graceland", Lists.newArrayList("tv_series"));
        addSource("http://hannibalpedia.wikia.com", "Hannibal_(TV_Series)", Lists.newArrayList("tv_series"));
        addSource("http://continuum.wikia.com", "Continuum", Lists.newArrayList("tv_series"));
        addSource("http://vikings.wikia.com", "Vikings", Lists.newArrayList("tv_series"));
        addSource("http://cbselementary.wikia.com", "Elementary", Lists.newArrayList("tv_series"));

        set.addAll(CouchSet.getSet());
        set.addAll(PredefinedCodSet.getSet());
        set.addAll(PredefinedErrorSet.getSet());
    }

    private static void addSource(String url, String title, Collection<String> features) {
        try {
            set.add(new InstanceSource(new URL(url), title, features));
        } catch (MalformedURLException e) {
            logger.debug("Cannot parse predefined url.", e);
        }

    }


    private static List<ClassifiedPage> getInstanceSources() throws IOException {
        List<InstanceSource> instanceSources = PredefinedGeneralSet.getSet();
        PageServiceFactory pageServiceFactory = new PageServiceFactory();
        List<ClassifiedPage> pageInfoList = new ArrayList<>();
        for(InstanceSource instanceSource: instanceSources) {
            PageService pageService = pageServiceFactory.get(instanceSource.getWikiRoot());
            PageInfo page;
            if ( instanceSource.getId() != null ) {
                page = pageService.getPage(instanceSource.getId());
                if( page == null ) {
                    logger.warn(String.format("Cannot fetch: %d (%s)", instanceSource.getId(), instanceSource.getWikiRoot()));
                    continue;
                }
            } else {
                page = pageService.getPage(instanceSource.getTitle());
                if( page == null ) {
                    logger.warn(String.format("Cannot fetch: %s (%s)", instanceSource.getTitle(), instanceSource.getWikiRoot()));
                    continue;
                }
            }
            pageInfoList.add(new ClassifiedPage(page, instanceSource));
        }
        return pageInfoList;
    }

    public static void main(String[] args) throws IOException {
        List<ClassifiedPage> pages = getInstanceSources();
        JsonArray jsonArray = new JsonArray();
        HashSet<String> recognisedTypes = Sets.newHashSet(
                "character"
                , "weapon"
                , "achievement"
                , "item"
                , "location"
                , "mini_game"
                , "flash_game"
                , "video_game"
                , "comic_book"
                , "music_recording"
                , "tv_series"
                , "tv_season"
                , "tv_episode"
                , "game"
                , "person"
                , "organization"
                , "book"
                , "movie");
        for( ClassifiedPage page: pages ) {
            String type = "other";
            for( String sourceType: page.instanceSource.getFeatures()) {
                if (recognisedTypes.contains(sourceType)) {
                    type = sourceType;
                }
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("wikiUrl", page.instanceSource.getWikiRoot().toString());
            jsonObject.addProperty("pageId", page.page.getPageId());
            jsonObject.addProperty("namespace", page.page.getNamespace());
            jsonObject.addProperty("wikiText", page.page.getWikiText());
            jsonObject.addProperty("title", page.page.getTitle());
            jsonObject.addProperty("type", type);
            jsonArray.add(jsonObject);
        }
        PrintWriter writer = new PrintWriter("hardcoded.json", "UTF-8");
        try {
            JsonWriter jsonWriter = new JsonWriter(writer);
            Gson gson = new GsonBuilder().create();
            gson.toJson(jsonArray, jsonWriter);
            jsonWriter.close();
        } finally {
            writer.close();
        }
    }

    static class ClassifiedPage {
        PageInfo page;
        InstanceSource instanceSource;

        ClassifiedPage(PageInfo page, InstanceSource instanceSource) {
            this.page = page;
            this.instanceSource = instanceSource;
        }
    }
}
