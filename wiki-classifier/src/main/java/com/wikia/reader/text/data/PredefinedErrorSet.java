package com.wikia.reader.text.data;/**
 * Author: Artur Dwornik
 * Date: 14.04.13
 * Time: 17:06
 */

import com.beust.jcommander.internal.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PredefinedErrorSet {
    private static Logger logger = LoggerFactory.getLogger(PredefinedErrorSet.class);

    public static List<InstanceSource> getSet() {
        return set;
    }

    private static final List<InstanceSource> set = new ArrayList<>();
    static {
        addSource("http://battlefield.wikia.com/", "Semper_Fidelis", Lists.newArrayList("level"));
        addSource("http://battlefield.wikia.com/", "Rock_and_a_Hard_Place", Lists.newArrayList("level"));


        addSource("http://callofduty.wikia.com/", "Salvatore_DeLuca", Lists.newArrayList("character"));
        addSource("http://callofduty.wikia.com/", "KSG_12", Lists.newArrayList("weapon"));
        addSource("http://callofduty.wikia.com/", "Concussion_Grenade", Lists.newArrayList("weapon"));
        addSource("http://friday.wikia.com/", "Craig", Lists.newArrayList("character"));
        addSource("http://jurassicpark.wikia.com/", "The_Lost_World_Film_Script", Lists.newArrayList("movie_script"));
        addSource("http://movies.wikia.com/", "Star_Trek_Into_Darkness", Lists.newArrayList("movie"));
        addSource("http://movies.wikia.com/", "Lincoln", Lists.newArrayList("movie"));
        addSource("http://onedirection.wikia.com/", "Jon_Shone", Lists.newArrayList("person"));
        addSource("http://onedirection.wikia.com/", "Glee", Lists.newArrayList("tv_show"));
        addSource("http://jurassicpark.wikia.com/", "Jurassic_Park:_Survival", Lists.newArrayList("game"));
        addSource("http://jurassicpark.wikia.com/", "Jurassic_Park_III", Lists.newArrayList("movie"));
        addSource("http://jurassicpark.wikia.com/", "The_Lost_World_(novel)", Lists.newArrayList("book"));
        addSource("http://jurassicpark.wikia.com/", "Books", Lists.newArrayList("person"));
        addSource("http://gaming.wikia.com/", "Fallout_3", Lists.newArrayList("game"));
        addSource("http://gaming.wikia.com/", "Marvel:_Ultimate_Alliance", Lists.newArrayList("game"));
        addSource("http://battlefield.wikia.com/", "M4_Sherman", Lists.newArrayList("vehicle"));
        addSource("http://taylorswift.wikia.com/", "Known_facts_about_Taylor_Swift", Lists.newArrayList("trivia"));
        addSource("http://onedirection.wikia.com/", "Jamie_Scott", Lists.newArrayList("person"));
        addSource("http://fallout.wikia.com/", "Old_World_Blues_(add-on)", Lists.newArrayList("dlc"));

        addSource("http://starcraft.wikia.com/", "Eldersthine", Lists.newArrayList("location", "planet"));
        addSource("http://masseffect.wikia.com/", "Mass_Effect_3", Lists.newArrayList("game"));
        addSource("http://literature.wikia.com/", "Life_of_Pi", Lists.newArrayList("book"));
        addSource("http://literature.wikia.com/", "Martha_Gellhorn", Lists.newArrayList("person", "writer"));
        addSource("http://literature.wikia.com/", "Djuna_Barnes", Lists.newArrayList("person", "writer"));
        addSource("http://literature.wikia.com/", "S._E._Hinton", Lists.newArrayList("person", "writer"));
        addSource("http://literature.wikia.com/", "My_Funny_Valentine", Lists.newArrayList("book"));
        addSource("http://literature.wikia.com/", "Higher Institute of Villainous Education", Lists.newArrayList("book"));
        addSource("http://literature.wikia.com/", "The_Bible", Lists.newArrayList("book"));


        addSource("http://thevoiceaustralia.wikia.com/", "Delta_Goodrem", Lists.newArrayList("person"));
        addSource("http://thevoiceaustralia.wikia.com/", "Season_2", Lists.newArrayList("tv_season"));
        addSource("http://movies.wikia.com/", "Quentin_Tarantino", Lists.newArrayList("person"));
        addSource("http://movies.wikia.com/", "Dwayne_Johnson", Lists.newArrayList("person"));
        addSource("http://movies.wikia.com/", "Django_Unchained", Lists.newArrayList("movie"));
        addSource("http://movies.wikia.com/", "Monty_Python_films", Lists.newArrayList("index"));
        addSource("http://masseffect.wikia.com/", "Batarian_War_Beast", Lists.newArrayList("unit"));
        addSource("http://movies.wikia.com/", "Prometheus", Lists.newArrayList("movie"));
        addSource("http://muppet.wikia.com/", "Jane_Henson", Lists.newArrayList("person"));
        addSource("http://muppet.wikia.com/", "Jim_Kroupa", Lists.newArrayList("person"));
        addSource("http://muppet.wikia.com/", "Episode_2096", Lists.newArrayList("tv_episode"));
        addSource("http://muppet.wikia.com/", "Season_17_(1985-1986)", Lists.newArrayList("tv_season"));
        addSource("http://muppet.wikia.com/", "Gonzo", Lists.newArrayList("character"));
        addSource("http://muppet.wikia.com/", "Caroll_Spinney", Lists.newArrayList("person"));

        addSource("http://naruto.wikia.com/", "Battle_on_the_Bridge!_Zabuza_Returns!", Lists.newArrayList("tv_episode"));
        addSource("http://naruto.wikia.com/", "Naruto_Shippūden:_Ultimate_Ninja_Mission", Lists.newArrayList("game"));
        addSource("http://naruto.wikia.com/", "Naruto:_Ninja_Council_3", Lists.newArrayList("game"));
        addSource("http://naruto.wikia.com/", "Tsunade", Lists.newArrayList("tv_episode"));

        addSource("http://harrypotter.wikia.com/", "Harry_Potter_and_the_Prisoner_of_Azkaban", Lists.newArrayList("book"));
        addSource("http://harrypotter.wikia.com/", "Harry_Potter_and_the_Prisoner_of_Azkaban_(film)", Lists.newArrayList("movie"));
        addSource("http://harrypotter.wikia.com/", "Harry_Potter_and_the_Prisoner_of_Azkaban_(video_game)", Lists.newArrayList("game"));
        addSource("http://harrypotter.wikia.com/", "Harry_Potter_and_Me", Lists.newArrayList("movie"));
        addSource("http://harrypotter.wikia.com/", "Emma_Watson", Lists.newArrayList("person"));
        addSource("http://harrypotter.wikia.com/", "Evanna_Lynch", Lists.newArrayList("person"));
        addSource("http://harrypotter.wikia.com/", "Mike_Newell", Lists.newArrayList("person"));

        addSource("http://muppet.wikia.com/", "School_(character)", Lists.newArrayList("character"));
        addSource("http://muppet.wikia.com/", "Darci", Lists.newArrayList("character"));
        addSource("http://muppet.wikia.com/", "Animal", Lists.newArrayList("character"));

        addSource("http://friends.wikia.com/", "Season_Two", Lists.newArrayList("tv_season"));
        addSource("http://friends.wikia.com/", "List_of_Friends_Episodes", Lists.newArrayList("tv_list_of_episodes"));
        addSource("http://friends.wikia.com/", "List_of_Joey_Episodes", Lists.newArrayList("tv_list_of_episodes"));
        addSource("http://friends.wikia.com/", "Joey_and_the_Student", Lists.newArrayList("tv_episode"));
        addSource("http://friends.wikia.com/", "Alexa_Junge", Lists.newArrayList("person"));
        addSource("http://friends.wikia.com/", "Marta_Kauffman", Lists.newArrayList("person"));
        addSource("http://friends.wikia.com/", "Cole_Sprouse", Lists.newArrayList("person"));


        addSource("http://callofduty.wikia.com/", "Estate", Lists.newArrayList("level","map"));
        addSource("http://starwars.wikia.com/", "Lost_Tribe_of_the_Sith:_Paragon", Lists.newArrayList("book"));
        addSource("http://starwars.wikia.com/", "Star_Wars_Episode_V:_The_Empire_Strikes_Back", Lists.newArrayList("movie"));
        addSource("http://starwars.wikia.com/", "Krayiss_Two", Lists.newArrayList("location"));
        addSource("http://tardis.wikia.com/", "BBC_Past_Doctor_Adventures", Lists.newArrayList("book"));
        addSource("http://fallout.wikia.com/", "Sean_McCoy", Lists.newArrayList("person"));
        addSource("http://callofduty.wikia.com/", "Overgrown", Lists.newArrayList("level","map"));
        addSource("http://halo.wikia.com/", "Halo:_The_Cole_Protocol", Lists.newArrayList("book"));
        addSource("http://halo.wikia.com/", "Halo_3", Lists.newArrayList("game"));
        addSource("http://halo.wikia.com/", "M9_High-Explosive_Dual-Purpose_grenade", Lists.newArrayList("weapon"));
        addSource("http://halo.wikia.com/", "Regenerator", Lists.newArrayList("item"));

        addSource("http://runescape.wikia.com/", "Mithril_armour", Lists.newArrayList("item", "armor"));
        addSource("http://runescape.wikia.com/", "Earth_warrior", Lists.newArrayList("unit"));
        addSource("http://runescape.wikia.com/", "Sumona", Lists.newArrayList("character"));
        addSource("http://runescape.wikia.com/", "Black_mace", Lists.newArrayList("weapon"));
        addSource("http://runescape.wikia.com/", "Rimmington", Lists.newArrayList("location"));
        addSource("http://runescape.wikia.com/", "Green_dragon", Lists.newArrayList("unit"));

        addSource("http://thehungergames.wikia.com/", "The_Hunger_Games:_Catching_Fire", Lists.newArrayList("movie"));
        addSource("http://thehungergames.wikia.com/", "Plutarch_Heavensbee", Lists.newArrayList("character"));
        addSource("http://thehungergames.wikia.com/", "The_Hunger_Games", Lists.newArrayList("book"));
        addSource("http://thehungergames.wikia.com/", "The_Hunger_Games_(film)", Lists.newArrayList("movie"));
        addSource("http://thehungergames.wikia.com/", "Elizabeth_Banks", Lists.newArrayList("person"));
        addSource("http://thehungergames.wikia.com/", "Josh_Hutcherson", Lists.newArrayList("person"));

        addSource("http://movies.wikia.com/", "Star_Wars_Episode_I:_The_Phantom_Menace", Lists.newArrayList("movie"));
        addSource("http://movies.wikia.com/", "Lilo_&_Stitch", Lists.newArrayList("movie"));
        addSource("http://movies.wikia.com/", "The_Godfather", Lists.newArrayList("movie"));
        addSource("http://movies.wikia.com/", "Kingdom_of_Heaven", Lists.newArrayList("movie"));

        addSource("http://muppet.wikia.com/", "Follow_That_Bird", Lists.newArrayList("movie"));
        addSource("http://sectorw.wikia.com/", "Half-Life_2:_Episode_One", Lists.newArrayList("game"));
        addSource("http://sectorw.wikia.com/", "Portal_2", Lists.newArrayList("game"));
        addSource("http://sectorw.wikia.com/", "Hunter", Lists.newArrayList("game"));
        addSource("http://sectorw.wikia.com/", "Ted_Backman", Lists.newArrayList("person"));
        addSource("http://sectorw.wikia.com/", "Mary_Kae_Irvin", Lists.newArrayList("person"));
        addSource("http://sectorw.wikia.com/", "Ivan_the_Space_Biker", Lists.newArrayList("character"));
        addSource("http://sectorw.wikia.com/", "Half-Life_2_original_storyline", Lists.newArrayList("storyline"));
        addSource("http://sectorw.wikia.com/", "MP7", Lists.newArrayList("weapon"));
        addSource("http://sectorw.wikia.com/", "Alien_Fauna", Lists.newArrayList("unit"));

        addSource("http://cnc.wikia.com/", "GDI_APC_(Renegade)", Lists.newArrayList("unit"));
        addSource("http://cnc.wikia.com/", "GD-3", Lists.newArrayList("weapon"));
        addSource("http://cnc.wikia.com/", "Mammoth_tank_(Renegade)", Lists.newArrayList("unit"));
        addSource("http://cnc.wikia.com/", "The_Shark_and_the_Lure", Lists.newArrayList("level"));
        addSource("http://cnc.wikia.com/", "The_Unfathomable_Fortress", Lists.newArrayList("level"));


        addSource("http://dcuniverseonline.wikia.com/", "Consumables", Lists.newArrayList("item"));
        addSource("http://dcuniverseonline.wikia.com/", "Gorilla_Island", Lists.newArrayList("level"));
        addSource("http://dcuniverseonline.wikia.com/", "Consumables", Lists.newArrayList("item"));

        addSource("http://dexter.wikia.com/", "Vince_Masuka", Lists.newArrayList("character"));
        addSource("http://dexter.wikia.com/", "Episode_307:_Easy_as_Pie", Lists.newArrayList("tv_episode"));
        addSource("http://dexter.wikia.com/", "Episode_608:_Sins_of_Omission", Lists.newArrayList("tv_episode"));
        addSource("http://dexter.wikia.com/", "Episode_208:_Morning_Comes", Lists.newArrayList("tv_episode"));
        addSource("http://dexter.wikia.com/", "Dexter_by_Design", Lists.newArrayList("book"));
        addSource("http://dexter.wikia.com/", "Jeff_Lindsay", Lists.newArrayList("person"));

        addSource("http://fallout.wikia.com/", "Marked_men", Lists.newArrayList("unit"));
        addSource("http://fallout.wikia.com/", "The_Divide", Lists.newArrayList("location"));
        addSource("http://fallout.wikia.com/", "Gobi_Campaign_scout_rifle", Lists.newArrayList("weapon"));
        addSource("http://fallout.wikia.com/", "K9000_cyberdog_gun", Lists.newArrayList("weapon"));
        addSource("http://fallout.wikia.com/", "Police_baton_(Fallout:_New_Vegas)", Lists.newArrayList("weapon"));
        addSource("http://fallout.wikia.com/", "Combat_armor_(Fallout_3)", Lists.newArrayList("item"));
        addSource("http://fallout.wikia.com/", "Merc_outfit_(Fallout_3)", Lists.newArrayList("item"));
        addSource("http://fallout.wikia.com/", "Sniper_rifle", Lists.newArrayList("weapon"));


        addSource("http://how-i-met-your-mother.wikia.com/", "Pamela_Fryman", Lists.newArrayList("person"));
        addSource("http://how-i-met-your-mother.wikia.com/", "Jason_Segel", Lists.newArrayList("person"));

        addSource("http://marvel.wikia.com/", "Spider-Man_(2002_film)", Lists.newArrayList("movie"));
        addSource("http://marvel.wikia.com/", "The_Amazing_Spider-Man_2_(film)", Lists.newArrayList("movie"));
        addSource("http://marvel.wikia.com/", "The_Amazing_Spider-Man_(2012_video_game)", Lists.newArrayList("game"));
        addSource("http://marvel.wikia.com/", "X-Men:_Chaos_Engine_Vol_1_2", Lists.newArrayList("book"));
        addSource("http://marvel.wikia.com/", "Wolverine_and_the_X-Men_(TV_series)", Lists.newArrayList("tv_series"));
        addSource("http://marvel.wikia.com/", "Frederick_Dukes_(Earth-8096)", Lists.newArrayList("character"));
        addSource("http://marvel.wikia.com/", "Wolverine_and_the_X-Men_(TV_series)_Season_1_6", Lists.newArrayList("tv_episode"));

        addSource("http://laracroft.wikia.com/", "Bookworm", Lists.newArrayList("achievement"));
        //addSource("http://laracroft.wikia.com/", "Father%27s_Secret_Study", Lists.newArrayList("level"));


        //addSource("http://dragonage.wikia.com/", "Warden%27s_Keep", Lists.newArrayList("dlc"));
        addSource("http://www.shadowhunters.wikia.com/", "The_Mortal_Instruments:_City_of_Bones", Lists.newArrayList("movie"));

        addSource("http://defiance.wikia.com/", "Tony_Curran", Lists.newArrayList("person"));
        addSource("http://defiance.wikia.com/", "Datak_Tarr", Lists.newArrayList("character"));
        addSource("http://simcity.wikia.com/", "SimCity_2000", Lists.newArrayList("game"));

        addSource("http://music.wikia.com/", "Paul_McCartney", Lists.newArrayList("person"));
        addSource("http://bioshock.wikia.com/", "BioShock_2", Lists.newArrayList("game"));
        addSource("http://dragonsdogma.wikia.com/", "Crossed_Cinqueda", Lists.newArrayList("weapon"));
        addSource("http://dragonsdogma.wikia.com/", "Caxton", Lists.newArrayList("character"));
        addSource("http://dragonsdogma.wikia.com/", "Divine_Surcoat", Lists.newArrayList("item"));

        addSource("http://firefly.wikia.com/", "Jayne_Cobb", Lists.newArrayList("character"));

        addSource("http://www.wowwiki.com/", "World_of_Warcraft", Lists.newArrayList("game"));
        addSource("http://firefly.wikia.com/", "Derrial_Book", Lists.newArrayList("character"));
        addSource("http://thelastofus.wikia.com/", "Toilet_paper", Lists.newArrayList("item"));
        addSource("http://harrypotter.wikia.com/", "Minerva_McGonagall", Lists.newArrayList("character"));
        addSource("http://harrypotter.wikia.com/", "Yvonne_Bampton", Lists.newArrayList("character"));
        addSource("http://harrypotter.wikia.com/", "Quidditch_Through_the_Ages_(real)", Lists.newArrayList("book"));
        addSource("http://thelastofus.wikia.com/", "9MM", Lists.newArrayList("weapon"));
        addSource("http://gta.wikia.com/", "Grand_Theft_Auto_IV", Lists.newArrayList("game"));
        addSource("http://gta.wikia.com/", "San_Fierro", Lists.newArrayList("location"));
        addSource("http://gta.wikia.com/", "An_Old_Friend", Lists.newArrayList("level"));
        addSource("http://gta.wikia.com/", "Grand_Theft_Auto:_London_1961", Lists.newArrayList("game"));
        addSource("http://gta.wikia.com/", "Niko_Bellic", Lists.newArrayList("character"));
        addSource("http://gta.wikia.com/", "Ruiner", Lists.newArrayList("vehicle", "car"));
        addSource("http://gta.wikia.com/", "Pizza_Boy_(mission)", Lists.newArrayList("level"));

        addSource("http://mario.wikia.com", "Mario_Party", Lists.newArrayList("game"));
        addSource("http://mario.wikia.com", "World_1-2_(Super_Mario_Bros.)", Lists.newArrayList("level"));
        addSource("http://mario.wikia.com", "Ice_Flower", Lists.newArrayList("item"));
        addSource("http://metrovideogame.wikia.com/", "Metro_2033_(Video_Game)", Lists.newArrayList("game"));
        addSource("http://metrovideogame.wikia.com/", "Metro_2033_(Novel)", Lists.newArrayList("book"));
        addSource("http://metrovideogame.wikia.com/" ,"VSV", Lists.newArrayList("weapon"));
        addSource("http://metrovideogame.wikia.com/", "Librarian", Lists.newArrayList("unit"));
        addSource("http://metrovideogame.wikia.com/", "Metro_Last_Light", Lists.newArrayList("game"));

        addSource("http://dragonball.wikia.com/", "Bujin_(Collectibles)", Lists.newArrayList("item"));

        addSource("http://movies.wikia.com/", "Pepper Potts", Lists.newArrayList("character"));
        addSource("http://movies.wikia.com/", "List_of_American_films_of_2012", Lists.newArrayList("index", "list"));
        //addSource("http://movies.wikia.com/", "Pete%27s_Dragon_(1977)", Lists.newArrayList("movie"));

        addSource("http://starwars.wikia.com/", "Battle_of_the_Contruum_system", Lists.newArrayList("event"));
        addSource("http://starwars.wikia.com/", "Blood_Carver", Lists.newArrayList("unit", "species"));
        addSource("http://starwars.wikia.com/", "New_Jedi_Order", Lists.newArrayList("organization"));
        addSource("http://starwars.wikia.com/", "DC-19_\"Stealth\"_carbine", Lists.newArrayList("weapon"));
        // addSource("http://starwars.wikia.com/", "Star_Wars%3A_Jedi", Lists.newArrayList("comic_book"));
        addSource("http://starwars.wikia.com/", "Force_Contention", Lists.newArrayList("rpg_addon"));
        addSource("http://starwars.wikia.com/", "Wizards_of_the_Coast", Lists.newArrayList("organization"));
        addSource("http://fallout.wikia.com/", "The_Family", Lists.newArrayList("organization"));
        addSource("http://dcuniverseonline.wikia.com/", "Justice_League_of_America", Lists.newArrayList("organization"));
        addSource("http://elderscrolls.wikia.com/", "Skyrim", Lists.newArrayList("location"));
        addSource("http://elderscrolls.wikia.com/", "Thieves_Guild_(Skyrim)", Lists.newArrayList("organization"));
        addSource("http://elderscrolls.wikia.com/", "Blade_of_Woe_(Skyrim)", Lists.newArrayList("weapon"));
        addSource("http://elderscrolls.wikia.com/", "Dragonbone_Mace", Lists.newArrayList("weapon"));
        addSource("http://elderscrolls.wikia.com/", "Forsworn_Axe", Lists.newArrayList("weapon"));
        addSource("http://elderscrolls.wikia.com/", "Dragonscale_Armor_(Armor_Piece)", Lists.newArrayList("armor", "item"));
        addSource("http://elderscrolls.wikia.com/", "Daedric_Artifacts_(Skyrim)", Lists.newArrayList("index"));

        addSource("http://elderscrolls.wikia.com/", "Clavicus_Vile's_Masque_(Skyrim)", Lists.newArrayList("helmet", "item"));
        addSource("http://elderscrolls.wikia.com/", "Spellbreaker_(Skyrim)", Lists.newArrayList("item"));

        addSource("http://starwars.wikia.com/", "Living_Force_(RPG)", Lists.newArrayList("rpg_stuff"));
        addSource("http://starwars.wikia.com/", "From_the_Trees", Lists.newArrayList("rpg_stuff"));
        addSource("http://starwars.wikia.com/", "Ewoks_1:_The_Rainbow_Bridge", Lists.newArrayList("comic"));
        addSource("http://starwars.wikia.com/", "Extracting_Aleece", Lists.newArrayList("rpg_stuff"));
        addSource("http://starwars.wikia.com/", "Do_No_Harm", Lists.newArrayList("story"));
        addSource("http://starwars.wikia.com/", "Enemy_Lines_I:_Rebel_Dream", Lists.newArrayList("book"));

        addSource("http://starwars.wikia.com/", "HK-51_Revealed", Lists.newArrayList("trailer"));
        addSource("http://starwars.wikia.com/", "Hope_(cinematic_trailer)", Lists.newArrayList("trailer"));
        addSource("http://starwars.wikia.com/", "Jedi_Training_Academy", Lists.newArrayList("performance"));
        addSource("http://starwars.wikia.com/", "Enemy_Lines_I:_Rebel_Dream", Lists.newArrayList("book"));
        addSource("http://starwars.wikia.com/", "X-wing:_Rogue_Squadron", Lists.newArrayList("book"));
        addSource("http://starwars.wikia.com/", "X-wing:_Wedge's_Gamble", Lists.newArrayList("book"));
        addSource("http://humanelement.wikia.com/", "Human_Element", Lists.newArrayList("game"));

        addSource("http://lotr.wikia.com/", "Movie_vs._Book:Fellowship_of_the_Ring", Lists.newArrayList("vs"));
        addSource("http://lotr.wikia.com/", "The Lord of the Rings (1978 film)", Lists.newArrayList("movie"));
        addSource("http://lotr.wikia.com/", "The Hobbit: The Desolation of Smaug", Lists.newArrayList("movie"));
        addSource("http://lotr.wikia.com/", "The_Return_of_the_Shadow", Lists.newArrayList("book"));
        addSource("http://lotr.wikia.com/", "Galadriel", Lists.newArrayList("character"));
        addSource("http://lotr.wikia.com/", "The Hobbit: The Desolation of Smaug", Lists.newArrayList("movie"));

        addSource("http://dcuniverseonline.wikia.com/", "Safe_Houses", Lists.newArrayList("location"));

        addSource("http://glee.wikia.com/", "Hunter", Lists.newArrayList("character"));
        addSource("http://glee.wikia.com/", "Nolan_Gerard_Funk", Lists.newArrayList("person"));
        addSource("http://glee.wikia.com/", "Trouty_Mouth", Lists.newArrayList("song"));
    }

    private static void addSource(String url, String title, Collection<String> features) {
        try {
            set.add(new InstanceSource(new URL(url), title, features));
        } catch (MalformedURLException e) {
            logger.debug("Cannot parse predefined url.", e);
        }

    }
}
