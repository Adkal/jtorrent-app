import javafx.util.Pair;
import tracker.TestUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by Adrian Kalata on 2017-12-18.
 */
public class test {
    public static void main(String ...args){
        System.out.println(generateThisShit());
    }

//    public static void main(String ...args){
//
//        String finalScript = "INSERT INTO public.dict_questionare(date_from) values ('"+ new java.util.Date() +"');\n";
//
//        System.out.println(finalScript + generateThisShit());
//    }

    private static java.util.List buildDictInsertsList(String data){
        return Arrays.stream(data.split("\n")).collect(Collectors.toMap(
                el -> el.split(",")[0], el -> el.split(",")[1]
        )).entrySet().stream().map(
                entry -> "INSERT INTO dictionary(dictionary_name, language, parent_value_id, value_code, value) VALUES ('CORE_PSAT_COUNTRY_ID', 'pl', null, "
                        + formatCountyName(entry.getValue()) + ", " + entry.getKey() + " );").collect(Collectors.toList());
    }

    private static String buildMifidDictList(String v, String ordinalNo, String content, String type, java.util.List<Pair<String, String>> answers){
        AtomicInteger ordinalCounter = new AtomicInteger(1);
        String q = "INSERT INTO dict_question(version_dict_questionares, ordinal_no, content, type, examination_type, questionnaire_type) VALUES " +
                "( "+ v + ", " +  ordinalNo + ", '" + content + "', '" + type + "', null, 'MIFID' );";

        String ans = answers.stream().map( an ->
                "INSERT INTO public.dict_answer(id_question_dict_question, ordinal_no, content, points)" +
                        "  VALUES ((SELECT MAX(id_question) FROM public.dict_question WHERE ordinal_no = " + ordinalNo + ")," +
                        ordinalCounter.getAndIncrement() +", '" + an.getKey() + "', " + an.getValue()+ ");").collect(Collectors.joining("\n")
        );
        return q + "\n" + ans;
    }

    private static String buildInsertsString(String data){
        return buildDictInsertsList(data).stream().collect(Collectors.joining("\n")).toString();
    }

    private static String formatCountyName(String text){
//        StringBuilder sb = new StringBuilder(text);
//        IntStream.range(0, text.length())
//                .filter(i -> i == 0 || i == 1 || text.charAt(i - 1) == ' ')
//                .forEach(i ->
//                        sb.setCharAt(i, Character.toUpperCase(text.charAt(i)))
//                );
//        return sb.toString();
//        return "'" + WordUtils.capitalize(text.substring(1, text.length() - 1)) + "'";
        return text.toLowerCase();
    }
    //
    private static String generateThisShit(){

        List a1 = TestUtil.getTestCollection(List.class, new Pair<>("nie znam żadnych instrumentów finansowych", "0"),
                new Pair<>("akcje", "2"), new Pair<>("obligacje", "1"), new Pair<>("jednostki funduszy inwestycyjnych", "3"),
                new Pair<>("inne", "1"));
        String q1 = buildMifidDictList("1", "1",
                "Jakie zna Pan/Pani instrumenty finansowe?","multipleChoiceQuestion", a1);

        List a2 = TestUtil.getTestCollection(List.class, new Pair<>("w ogóle nie inwestuję w instrumenty finansowe", "0"),
                new Pair<>("krócej niż rok", "1"), new Pair<>("od 1 roku do 5 lat", "2"), new Pair<>("powyżej 5 lat", "4"));
        String q2 = buildMifidDictList("1", "2",
                "Od kiedy inwestuje Pan/Pani w instrumenty finansowe?","choiceQuestion", a2);

        List a3 = TestUtil.getTestCollection(List.class, new Pair<>("0", "3"),
                new Pair<>("do 10 tyś. zł", "1"), new Pair<>("powyżej 10 tyś zł. do 100 tyś. zł", "2"),
                new Pair<>("powyżej 100 tyś. zł", "4"));
        String q3 = buildMifidDictList("1", "3",
                "Jaką kwotę zainwestował/a Pan/Pani w instrumenty finansowe, w okresie o którym mowa w pytaniu powyżej?","choiceQuestion", a3);


        List a4 = TestUtil.getTestCollection(List.class, new Pair<>("0", "3"),
                new Pair<>("do 10", "1"), new Pair<>("powyżej 10 do 50", "2"),
                new Pair<>("powyżej 50", "4"));
        String q4 = buildMifidDictList("1", "4",
                "Ile transakcji na instrumentach finansowych wykonał/a Pan/Pani w trakcie ostatniego roku?","choiceQuestion", a4);

        List a5 = TestUtil.getTestCollection(List.class,
                new Pair<>("nie akceptuję ryzyka inwestycyjnego ani możliwości poniesienia strat", "0"),
                new Pair<>("akceptuję ryzyko spadku wartości inwestycji do około 10%", "2"),
                new Pair<>("akceptuję ryzyko spadku wartości inwestycji do około 20%", "4"),
                new Pair<>("akceptuję ryzyko poniesienia znacznych strat powyżej 20%", "6"));
        String q5 = buildMifidDictList("1", "5",
                "Prosimy o określenie własnego stopnia akceptacji ryzyka inwestycyjnego.","choiceQuestion", a5);

        List a6 = TestUtil.getTestCollection(List.class, new Pair<>("wyższe ekonomiczne na kierunkach związanych z rynkami finansowymi", "4"),
                new Pair<>("inne wyższe", "2"), new Pair<>("średnie ekonomiczne", "1"), new Pair<>("pozostałe", "0"));
        String q6 = buildMifidDictList("1", "6",
                "Prosimy o wskazanie posiadanego wykształcenia.","choiceQuestion", a6);


        List a7 = TestUtil.getTestCollection(List.class, new Pair<>("pracuję w sektorze finansowym na stanowisku, które wymaga wiedzy o instrumentach finansowych", "3"),
                new Pair<>("inny", "0"));
        String q7 = buildMifidDictList("1", "7",
                "Prosimy o wskazanie obecnie wykonywanego przez Pana/Panią zawodu.","choiceQuestion", a7);


        List a8 = TestUtil.getTestCollection(List.class, new Pair<>("zgadzam się", "3"),
                new Pair<>("nie zgadzam się", "0"));
        String q8 = buildMifidDictList("1", "8",
                "Prosimy o zaznaczenie stwierdzeń, z którymi się Pan/Pani zgadza: fundusze inwestycyjne różnią się oczekiwanym zyskiem oraz poziomem ryzyka inwestycyjnego","choiceQuestion", a8);

        List a9 = TestUtil.getTestCollection(List.class, new Pair<>("zgadzam się", "3"),
                new Pair<>("nie zgadzam się", "0"));
        String q9 = buildMifidDictList("1", "9",
                "Prosimy o zaznaczenie stwierdzeń, z którymi się Pan/Pani zgadza: fundusze inwestycyjne nie gwarantują osiągnięcia zysku, a historyczne wyniki funduszy nie stanowią gwarancji uzyskania podobnych wyników w przyszłości","choiceQuestion", a9);

        List a10 = TestUtil.getTestCollection(List.class, new Pair<>("zgadzam się", "3"),
                new Pair<>("nie zgadzam się", "0"));
        String q10 = buildMifidDictList("1", "10",
                "Prosimy o zaznaczenie stwierdzeń, z którymi się Pan/Pani zgadza: zalecany okres inwestowania zależy od wybranego funduszu inwestycyjnego","choiceQuestion", a10);

        List a11 = TestUtil.getTestCollection(List.class,
                new Pair<>("niewielki", "0"),
                new Pair<>("przeciętny", "1"),
                new Pair<>("znaczący", "3"));
        String q11 = buildMifidDictList("1", "11",
                "Jaki udział w Pana/Pani aktywach finansowych stanowić będzie inwestycja w fundusze inwestycyjne?",
                "choiceQuestion", a11);

        return q1 + "\n\n" + q2 + "\n\n" + q3 + "\n\n" + q4 + "\n\n" + q5 + "\n\n" + q6 + "\n\n" + q7 +
                "\n\n" + q8 + "\n\n" + q9 + "\n\n" + q10 + "\n\n" + q11;

    }
////
//    private static void test() {
//        Question a = new Question("matma", 1, 0);
//        Question a1 = new Question("matma", 10, 1);
//        Question a2 = new Question("polski", 5, 0);
//        Question a3 = new Question("polski", 1, 1);
//        Question a4 = new Question("matma", 2, 0);
//        Question a5 = new Question("matma", 4, 1);
//
//        List questionss = TestUtil.getTestCollection(ArrayList.class, a, a1, a2, a3, a4, a5);
//
//        ((Map<String, java.util.List>) questionss.stream().collect(Collectors.groupingBy(Question::getSubject));
//
//    }
}
