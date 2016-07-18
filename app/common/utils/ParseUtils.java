package common.utils;

import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

/**
 * @author iRevThis
 */
public final class ParseUtils {

    private static boolean validF(String s) {
        return !s.contains("icon.skilltransform") && !s.contains("icon.skill_")
                && !s.contains("BranchSys2.icon") && !s.contains("_dwarf")
                && !s.contains("boss") && !s.contains("_new")
                && !s.contains("_1") && !s.contains("_2")
                && !s.contains("_3") && !s.contains("_4")
                && !s.contains("_5") && !s.contains("_6")
                && !s.contains("_7") && !s.contains("_8")
                && !s.contains("_9") && !s.contains("_0")
                && !s.contains("raid") && !s.contains("_x10")
                && !s.contains("_x11") && !s.contains("_x12")
                && !s.contains("_x13") && !s.contains("_x14")
                && !s.contains("_x15") && !s.contains("_x16")
                && !s.contains("_x17") && !s.contains("_x18")
                && !s.contains("_x19") && !s.contains("_x20")
                && !s.contains("_max") && !s.contains("_normal")
                && !s.contains("_human") && !s.contains("_elf")
                && !s.contains("_orc") && !s.contains("_darkelf")
                && !s.contains("_etc") && !s.contains("4416")
                && !s.contains("5008") && !s.contains("4416")
                && s.contains("icon.skill") && (s.length() - 10 - s.lastIndexOf("icon.skill") == 4)
                ;
    }

    private static int counter = 0;

    public static void incVal() {
        counter++;
    }

    final long String = 0;

    public static void main(String[] args) throws Exception {
        // parseSkillsData();
        //parseNpcsData();
        parseItemsData();

        /*Socket sock = new Socket();
        sock.connect(new InetSocketAddress("localhost", 7778));

        for (int i = 0; i < 10; i++) {
            Socket socks = new Socket();
            socks.connect(new InetSocketAddress("localhost", 7778));
            socks.getOutputStream().write(new byte[] { 0, 4, 7, 1});
        }

        Thread.sleep(999999);*/
    }

    private static void parseSkillsData() {
        final PrintWriter writer;
        try {

            writer = new PrintWriter(new File("data\\forParse\\output\\skill_data.txt"), "UTF-8");
            Path path = Paths.get(new File("data\\forParse").getAbsolutePath(), "skills.txt");

            Files.lines(path).sorted((s1, s2) -> {
                String[] arr1 = s1.split(" ");
                String[] arr2 = s2.split(" ");
                //if (!JavaGameData.isNumeric(arr1[0]) || !JavaGameData.isNumeric(arr2[0]))
                //return 0;

                return Integer.valueOf(arr1[0]).compareTo(Integer.valueOf(arr2[0]));
            }).distinct().forEach(s -> {
                incVal();
                String[] arr = s.split(" ");

                StringBuilder skillName = new StringBuilder();
                IntStream.range(4, arr.length)
                        .forEach(i -> skillName.append(arr[i].replace("\\0", "")).append(" "));

                if (arr[1].contains("icon.skill") && validF(arr[1])) {
                    arr[1] = arr[1].replace("icon.skill", "");

                    //if (Integer.parseInt(arr[1]) != 0)
                    writer.write(arr[0] + " " + Integer.parseInt(arr[1]) + " " + arr[2] + " " + arr[3] + " " + skillName + "\n");
                    //else
                    //    writer.write(arr[0] + " " + arr[1] + " " + arr[2] + "\n");
                } else
                    writer.write(arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + skillName + "\n");
            });


            System.out.println(counter);
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            //writer.close();
        }
    }

    private static void parseNpcsData() {
        final PrintWriter writer;
        try {

            writer = new PrintWriter(new File("data\\forParse\\output\\npc_data.txt"), "UTF-8");
            Path path = Paths.get(new File("data\\forParse").getAbsolutePath(), "npcs.txt");

            Files.lines(path).sorted((s1, s2) -> {
                String[] arr1 = s1.split(" ");
                String[] arr2 = s2.split(" ");
                //if (!JavaGameData.isNumeric(arr1[0]) || !JavaGameData.isNumeric(arr2[0]))
                //return 0;

                return Integer.valueOf(arr1[0]).compareTo(Integer.valueOf(arr2[0]));
            }).distinct().forEach(s -> {
                incVal();
                String[] arr = s.split(" ");

                StringBuilder npcName = new StringBuilder();
                IntStream.range(1, arr.length)
                        .forEach(i -> npcName.append(arr[i].replace("\\0", "")).append(" "));

                writer.write(arr[0] + " " + npcName + "\n");
            });


            System.out.println(counter);
        } catch (Exception e) {
            System.out.println("" + e);
        } finally {
            //writer.close();
        }
    }

    private static void parseItemsData() {
        final PrintWriter writer;
        try {

            writer = new PrintWriter(new File("data\\forParse\\output\\item_data.txt"), "UTF-8");
            Path path = Paths.get(new File("data\\forParse").getAbsolutePath(), "items.txt");

            Files.lines(path).sorted((s1, s2) -> {
                String[] arr1 = s1.split(" ");
                String[] arr2 = s2.split(" ");
                //if (!JavaGameData.isNumeric(arr1[0]) || !JavaGameData.isNumeric(arr2[0]))
                //return 0;

                return Integer.valueOf(arr1[0]).compareTo(Integer.valueOf(arr2[0]));
            }).distinct().forEach(s -> {
                incVal();
                String[] arr = s.split(" ");
                StringBuilder itemName = new StringBuilder();
                IntStream.range(3, arr.length)
                        .forEach(i -> itemName.append(arr[i].replace("\\0", "")).append(" "));

                writer.write(arr[0] + " " + arr[1] + " " + arr[2] + " " + itemName + "\n");
            });


            System.out.println(counter);
        } catch (Exception e) {
            //System.out.println("" + );
            e.printStackTrace();
        } finally {
            //writer.close();
        }
    }
}
