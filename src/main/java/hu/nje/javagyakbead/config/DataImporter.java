
package hu.nje.javagyakbead.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class DataImporter implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public DataImporter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        importFilmek();
        importMozik();
        importEloadasok();
    }

    private void importFilmek() throws Exception {

        ClassPathResource resource =
                new ClassPathResource("data/film.txt");

        int imported = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        resource.getInputStream(),
                        StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split("\t", -1);

                if (data.length != 4) {
                    throw new IllegalArgumentException(
                            "Hibás filmadat: " + line);
                }

                Long id = Long.parseLong(data[0].trim());
                String cim = data[1].trim();
                Integer ev = Integer.parseInt(data[2].trim());
                Integer hossz = Integer.parseInt(data[3].trim());

                int inserted = jdbcTemplate.update(
                        """
                        INSERT INTO film (id, cim, ev, hossz)
                        VALUES (?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE id = id
                        """,
                        id, cim, ev, hossz
                );

                if (inserted > 0) {
                    imported++;
                }
            }
        }

        System.out.println(
                "Film importálás kész. Új filmek: " + imported);
    }

    private void importMozik() throws Exception {

        ClassPathResource resource =
                new ClassPathResource("data/mozi.txt");

        int imported = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        resource.getInputStream(),
                        StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split("\t", -1);

                if (data.length != 4) {
                    throw new IllegalArgumentException(
                            "Hibás moziadat: " + line);
                }

                Long id = Long.parseLong(data[0].trim());
                String nev = data[1].trim();
                String varos = data[2].trim();
                Integer ferohely =
                        Integer.parseInt(data[3].trim());

                int inserted = jdbcTemplate.update(
                        """
                        INSERT INTO mozi (id, nev, varos, ferohely)
                        VALUES (?, ?, ?, ?)
                        ON DUPLICATE KEY UPDATE id = id
                        """,
                        id, nev, varos, ferohely
                );

                if (inserted > 0) {
                    imported++;
                }
            }
        }

        System.out.println(
                "Mozi importálás kész. Új mozik: " + imported);
    }

    private void importEloadasok() throws Exception {

        ClassPathResource resource =
                new ClassPathResource("data/eloadas.txt");

        Long existingCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM eloadas",
                Long.class
        );

        if (existingCount != null && existingCount > 0) {
            System.out.println(
                    "Előadások importálása kihagyva: " +
                            existingCount + " rekord már létezik."
            );
            return;
        }

        int imported = 0;

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        resource.getInputStream(),
                        StandardCharsets.UTF_8))) {

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                if (line.isBlank()) {
                    continue;
                }

                String[] data = line.split("\t", -1);

                if (data.length != 5) {
                    throw new IllegalArgumentException(
                            "Hibás előadásadat: " + line);
                }

                Long filmId = Long.parseLong(data[0].trim());
                Long moziId = Long.parseLong(data[1].trim());

                LocalDate datum = LocalDate.parse(
                        data[2].trim(),
                        DATE_FORMAT
                );

                Integer nezoszam =
                        Integer.parseInt(data[3].trim());

                Long bevetel =
                        Long.parseLong(data[4].trim());

                jdbcTemplate.update(
                        """
                        INSERT INTO eloadas
                        (filmid, moziid, datum, nezoszam, bevetel)
                        VALUES (?, ?, ?, ?, ?)
                        """,
                        filmId,
                        moziId,
                        datum,
                        nezoszam,
                        bevetel
                );

                imported++;
            }
        }

        System.out.println(
                "Előadások importálása kész. Új előadások: " +
                        imported
        );
    }
}
