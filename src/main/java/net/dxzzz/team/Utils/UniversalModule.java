package net.dxzzz.team.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.dxzzz.team.Team;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.event.EventPriority;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class UniversalModule {
    private static final List<String> options = Arrays.asList("MONITOR","HIGHEST","HIGH","NORMAL","LOW","LOWEST");
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int ID_LENGTH = 6;
    private static final Random random = new Random();
    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 6;
    private static final Pattern VALID_CHAR_PATTERN = Pattern.compile("^[\\u4E00-\\u9FFF\\w]+$");

    public static boolean isNameValid(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length < MIN_LENGTH || length > MAX_LENGTH) {
            return false;
        }
        return VALID_CHAR_PATTERN.matcher(str).matches();
    }

    public static String generateUniqueID() {
        List <String> existingIDs = Team.GetDatabaseManager().GetAllTeamNames();
        String id;
        do {
            id = generateRandomID();
        } while (existingIDs.contains(id));
        existingIDs.add(id);
        return id;
    }

    private static String generateRandomID() {
        StringBuilder sb = new StringBuilder(ID_LENGTH);
        for (int i = 0; i < ID_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static String ListToString(List<?> list){
        Gson gson = new Gson();
        String s = gson.toJson(list);
        return s;
    }
    public static List<String>  StringToList(String jsonString) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<String>>(){}.getType();
        List<String> stringList = gson.fromJson(jsonString, listType);
        return stringList;
    }



    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateReasonString(String str) {
        // 检查是否只包含中文、英文、数字
        for (char ch : str.toCharArray()) {
            if (!(Character.isLetterOrDigit(ch) || isChinese(ch))) {
                return false;
            }
        }

        // 计算总长度
        int length = 0;
        for (char ch : str.toCharArray()) {
            if (isChinese(ch)) {
                length += 2; // 中文字符长度为2
            } else {
                length += 1; // 英文字符和数字长度为1
            }
        }

        // 检查总长度是否大于20
        return length < 20;
    }

    public static boolean validateGhNameString(String str) {
        // 检查是否只包含中文、英文、数字
        for (char ch : str.toCharArray()) {
            if (!(Character.isLetterOrDigit(ch) || isChinese(ch))) {
                return false;
            }
        }

        // 计算总长度
        int length = 0;
        for (char ch : str.toCharArray()) {
            if (isChinese(ch)) {
                length += 2; // 中文字符长度为2
            } else {
                length += 1; // 英文字符和数字长度为1
            }
        }

        // 检查总长度是否大于20
        return length <= 14;
    }

    // 判断字符是否是中文
    private static boolean isChinese(char ch) {
        return ch >= 0x4E00 && ch <= 0x9FFF;
    }


    //判断输入的优先级参数是否正确
    public static final boolean isTalkPriorityParamTrue(String param){
        for(String option: options){
            if(param.equals(option)){
                return true;
            }
        }
        return false;
    }

    public static final EventPriority GetEventPriority(String param){
        if(param.equalsIgnoreCase("MONITOR")){
            return EventPriority.MONITOR;
        }
        else if(param.equalsIgnoreCase("HIGHEST")){
            return EventPriority.HIGHEST;
        }
        else if(param.equalsIgnoreCase("HIGH")){
            return EventPriority.HIGH;
        }
        else if(param.equalsIgnoreCase("NORMAL")){
            return EventPriority.NORMAL;
        }
        else if(param.equalsIgnoreCase("LOW")){
            return EventPriority.LOW;
        }
        else {
            return EventPriority.LOWEST;
        }
    }
    public static List<Pair<Integer, Long>> MergeLists(List<Integer> intList, List<Long> longList) {
        if (intList.size() != longList.size()) {
            System.out.println("合并列表出现异常");
            return new ArrayList<>();
        }
        List<Pair<Integer, Long>> result = new ArrayList<>();

        for (int i = 0; i < intList.size(); i++) {
            result.add(Pair.of(intList.get(i), longList.get(i)));
        }
        return result;
    }
}
