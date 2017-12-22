import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LineMatcherTest {
    @Test
    void parse() {
        LineMatcher lineMatcher = new LineMatcher();

        assertTrue(lineMatcher.getIsConstitution());
        TypeAndIndex typeAndIndex = lineMatcher.parse("USTAWA JANUSZA ĄĘŹĆŻŚŃĆÓŁ");
        assertTrue(typeAndIndex.getIndex().equals("") && typeAndIndex.getType() == ActElementType.Podsekcja);
        typeAndIndex = lineMatcher.parse("Rozdział IVA");
        assertTrue(typeAndIndex.getIndex().equals("IVA") && typeAndIndex.getType() == ActElementType.Sekcja);

        typeAndIndex = lineMatcher.parse("DZIAŁ IVA");
        assertTrue(typeAndIndex.getIndex().equals("IVA") && typeAndIndex.getType() == ActElementType.Sekcja);
        assertFalse(lineMatcher.getIsConstitution());
        typeAndIndex = lineMatcher.parse("Rozdział 1a");
        assertTrue(typeAndIndex.getIndex().equals("") && typeAndIndex.getType() == ActElementType.Podsekcja);


        typeAndIndex = lineMatcher.parse("Art. 13a.");
        assertTrue(typeAndIndex.getIndex().equals("13a") && typeAndIndex.getType() == ActElementType.Artykul);

        typeAndIndex = lineMatcher.parse("1a. Zamiar koncentracji podlega zgłoszeniu Prezesowi Urzędu, jeżeli:");
        assertTrue(typeAndIndex.getIndex().equals("1a") && typeAndIndex.getType() == ActElementType.Ustep);

        typeAndIndex = lineMatcher.parse("2a) łączny obrót na terytorium Rzeczypospolitej Polskiej przedsiębiorców");
        assertTrue(typeAndIndex.getIndex().equals("2a") && typeAndIndex.getType() == ActElementType.Punkt);

        typeAndIndex = lineMatcher.parse("c) członkowie jego zarządu lub rady nadzorczej stanowią więcej niż połowę");
        assertTrue(typeAndIndex.getIndex().equals("c") && typeAndIndex.getType() == ActElementType.Litera);

        typeAndIndex = lineMatcher.parse("przedsiębiorców oraz między przedsiębiorcami i ich związkami albo");
        assertTrue(typeAndIndex.getIndex().equals("") && typeAndIndex.getType() == ActElementType.Tekst);
    }

}