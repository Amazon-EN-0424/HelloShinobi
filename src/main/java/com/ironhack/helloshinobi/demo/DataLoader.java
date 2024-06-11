package com.ironhack.helloshinobi.demo;

import com.ironhack.helloshinobi.model.Clan;
import com.ironhack.helloshinobi.model.Mission;
import com.ironhack.helloshinobi.model.Ninja;
import com.ironhack.helloshinobi.repository.ClanRepository;
import com.ironhack.helloshinobi.repository.MissionRepository;
import com.ironhack.helloshinobi.repository.NinjaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private NinjaRepository ninjaRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private ClanRepository clanRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Create and save entities
        Clan clan = new Clan();
        clan.setName("Shadow Clan");
        clan.setVillage("Hidden Leaf Village");
        clanRepository.save(clan);

        Mission mission1 = new Mission();
        mission1.setName("Rescue Mission");
        mission1.setDescription("Rescue the kidnapped villagers.");
        missionRepository.save(mission1);

        Mission mission2 = new Mission();
        mission2.setName("Espionage Mission");
        mission2.setDescription("Gather intelligence from the enemy territory.");
        missionRepository.save(mission2);

        Ninja ninja = new Ninja();
        ninja.setName("Kakashi Hatake");
        ninja.setRank("Jonin");
        ninja.setClan(clan);
        ninja.getMissions().add(mission1);
        ninja.getMissions().add(mission2);
        ninjaRepository.save(ninja);

        // Fetch and display data to demonstrate eager loading
        List<Ninja> ninjas = ninjaRepository.findAll();
        System.out.println("Ninjas and their missions (EAGER loading):");
        for (Ninja n : ninjas) {
            System.out.println("Ninja: " + n.getName() + ", Rank: " + n.getRank() + ", Missions: " + n.getMissions());
        }

        // Fetch and display data to demonstrate lazy loading
        Ninja fetchedNinja = ninjaRepository.findById(ninja.getId()).orElse(null);
        if (fetchedNinja != null) {
            System.out.println("Fetched Ninja: " + fetchedNinja.getName());
            // Access the lazy-loaded property
            System.out.println("Ninja's Clan (LAZY loading): " + fetchedNinja.getClan().getName());
        }
    }
}
