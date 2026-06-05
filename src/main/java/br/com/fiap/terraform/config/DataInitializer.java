package br.com.fiap.terraform.config;

import br.com.fiap.terraform.entity.AirState;
import br.com.fiap.terraform.entity.Greenhouse;
import br.com.fiap.terraform.entity.InventoryItem;
import br.com.fiap.terraform.entity.OperationLog;
import br.com.fiap.terraform.entity.Planet;
import br.com.fiap.terraform.entity.PlantState;
import br.com.fiap.terraform.entity.SoilState;
import br.com.fiap.terraform.enums.GreenhouseStatus;
import br.com.fiap.terraform.enums.GrowthPhase;
import br.com.fiap.terraform.enums.InventoryResourceType;
import br.com.fiap.terraform.enums.LogType;
import br.com.fiap.terraform.repository.GreenhouseRepository;
import br.com.fiap.terraform.repository.OperationLogRepository;
import br.com.fiap.terraform.repository.PlanetRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(PlanetRepository planetRepository,
            GreenhouseRepository greenhouseRepository,
            OperationLogRepository operationLogRepository) {
        return args -> {
            if (planetRepository.count() > 0) {
                return;
            }

            Planet moon = planetRepository.save(new Planet("lua", "Lua", bd("0.17"), "#A0A0A8"));
            Planet mars = planetRepository.save(new Planet("marte", "Marte", bd("0.38"), "#C1440E"));
            Planet europa = planetRepository.save(new Planet("europa", "Europa", bd("0.13"), "#4488CC"));
            Planet titan = planetRepository.save(new Planet("tita", "Tita", bd("0.14"), "#D4A017"));
            Planet earth = planetRepository.save(new Planet("terra", "Terra", bd("1.00"), "#2E8B57"));

            Greenhouse selene = saveGreenhouse(greenhouseRepository, moon, "Estufa Selene-01", "alface",
                    GrowthPhase.VEGETATIVE, GreenhouseStatus.OPERATIONAL, healthySoil(), healthyAir());
            Greenhouse ares = saveGreenhouse(greenhouseRepository, mars, "Estufa Ares-01", "tomate",
                    GrowthPhase.FLOWERING, GreenhouseStatus.OPERATIONAL, healthySoil(), healthyAir());
            Greenhouse callisto = saveGreenhouse(greenhouseRepository, europa, "Estufa Callisto-01", "soja",
                    GrowthPhase.VEGETATIVE, GreenhouseStatus.CRITICAL, lowPhosphorusSoil(), healthyAir());
            Greenhouse titanOne = saveGreenhouse(greenhouseRepository, titan, "Estufa Tita-01", "cenoura",
                    GrowthPhase.SEEDLING, GreenhouseStatus.OPERATIONAL, healthySoil(), healthyAir());
            Greenhouse terraOne = saveGreenhouse(greenhouseRepository, earth, "Estufa Terra-01", "alface",
                    GrowthPhase.HARVEST, GreenhouseStatus.OPERATIONAL, healthySoil(), healthyAir());

            operationLogRepository.saveAll(List.of(
                    new OperationLog(selene, LogType.READING, null, "Estado inicial: estufa operacional na Lua."),
                    new OperationLog(ares, LogType.READING, null, "Estado inicial: estufa operacional em Marte."),
                    new OperationLog(callisto, LogType.ALERT, br.com.fiap.terraform.enums.AlertLevel.CRITICAL,
                            "Fósforo crítico detectado em Europa."),
                    new OperationLog(titanOne, LogType.READING, null, "Estado inicial: estufa operacional em Titã."),
                    new OperationLog(terraOne, LogType.GROWTH, null, "Planta pronta para colheita.")
            ));
        };
    }

    private Greenhouse saveGreenhouse(GreenhouseRepository repository, Planet planet, String name, String species,
            GrowthPhase phase, GreenhouseStatus status, SoilState soil, AirState air) {
        Greenhouse greenhouse = new Greenhouse(
                name,
                status,
                planet,
                soil,
                air,
                new PlantState(species, phase, phase == GrowthPhase.HARVEST ? bd("100") : bd("42"))
        );
        addDefaultInventory(greenhouse);
        return repository.save(greenhouse);
    }

    private void addDefaultInventory(Greenhouse greenhouse) {
        add(greenhouse, "N", InventoryResourceType.RAW_ELEMENT, "80");
        add(greenhouse, "P", InventoryResourceType.RAW_ELEMENT, "75");
        add(greenhouse, "K", InventoryResourceType.RAW_ELEMENT, "70");
        add(greenhouse, "Ca", InventoryResourceType.RAW_ELEMENT, "65");
        add(greenhouse, "Mg", InventoryResourceType.RAW_ELEMENT, "60");
        add(greenhouse, "S", InventoryResourceType.RAW_ELEMENT, "62");
        add(greenhouse, "O", InventoryResourceType.RAW_ELEMENT, "78");
        add(greenhouse, "H", InventoryResourceType.RAW_ELEMENT, "72");
        add(greenhouse, "C", InventoryResourceType.RAW_ELEMENT, "68");
        add(greenhouse, "H2O", InventoryResourceType.SYNTHESIZED_COMPOUND, "60");
        add(greenhouse, "NH3", InventoryResourceType.SYNTHESIZED_COMPOUND, "40");
        add(greenhouse, "CaCO3", InventoryResourceType.SYNTHESIZED_COMPOUND, "35");
        add(greenhouse, "H2CO3", InventoryResourceType.SYNTHESIZED_COMPOUND, "25");
    }

    private void add(Greenhouse greenhouse, String code, InventoryResourceType type, String percentage) {
        greenhouse.addInventoryItem(new InventoryItem(code, type, bd(percentage)));
    }

    private SoilState healthySoil() {
        return new SoilState(bd("6.5"), bd("55"), bd("85"), bd("75"), bd("65"), bd("70"), bd("60"),
                bd("55"), bd("58"));
    }

    private SoilState lowPhosphorusSoil() {
        return new SoilState(bd("6.4"), bd("52"), bd("48"), bd("70"), bd("11"), bd("68"), bd("58"),
                bd("52"), bd("55"));
    }

    private AirState healthyAir() {
        return new AirState(bd("21.0"), bd("0.04"), bd("62"), bd("90"));
    }

    private BigDecimal bd(String value) {
        return new BigDecimal(value);
    }
}
