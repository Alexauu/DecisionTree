package com.alexauu;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

class utils {


    private static List<String[]> datas = new ArrayList();
    private static String[] attributes;
    static Map<String, List<String>> attribute_value = new HashMap<>();
    static String[] clazz = new String[2];


    static void importData() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream("data4")));
        String reader = bufferedReader.readLine();
        attributes = reader.split(" ");
        clazz = bufferedReader.readLine().split(" ");
        while ( (reader = bufferedReader.readLine()) != null ){
            datas.add(reader.split(" "));
        }
        utils.setAttribute_Value(attribute_value);

    }

    private static double entropy(List<String[]> S){
        int countYes = 0;
        int total = S.size();

        for (String[] str: S) {
            if (str[str.length-1].equals(clazz[0])){
                countYes++;
            }
        }
        int countNo = total-countYes;
        double yes = countYes*1.0/total;
        double no = countNo*1.0/total;
        double result = -(yes*Math.log(yes)/Math.log(2))-(no*Math.log(no)/Math.log(2));
        return Double.isNaN(result)?0:result;
    }

    private static double entropy(List<String[]> S, String attribute){
        int total = S.size();

        double result = 0;
        Map<String, Integer> attributeCount = getAttributeCount(S, attribute);

        for (String s : attributeCount.keySet()) {
            result += attributeCount.get(s)*1.0/total * entropy(getDataList(S,attribute,s));
        }
        return Double.isNaN(result)?0:result;
    }

    private static double split_info(List<String[]> S, String attribute){
        int total = S.size();
        double result = 0;
        Map<String, Integer> attributeCount = getAttributeCount(S, attribute);
        for (Integer integer : attributeCount.values()) {
            double scale = integer*1.0/total;
            result -= (scale * Math.log(scale) / Math.log(2));
        }
        return Double.isNaN(result)?0:result;
    }

    private static double gain_ratio(List<String[]> S, String attribute){
        double result = gain(S, attribute) / split_info(S, attribute);
        return Double.isNaN(result)?0:result;
    }

    private static double gain(List<String[]> S, String attribute){
        return entropy(S) - entropy(S, attribute);
    }

    static String getNodeByID3(List<String[]> S){
        int length = attributes.length;
        double max = gain(S, attributes[0]);
        int maxI = 0;
        int i;
        for (i = 0; i < length-1; i++) {
            double gain = gain(S, attributes[i]);
            if (gain > max) {
                max = gain;
                maxI = i;
            }
        }
        return attributes[maxI];
    }

    static String getNodeByC45(List<String[]> S){
        int length = attributes.length;
        double max = gain_ratio(S, attributes[0]);
        int maxI = 0;
        int i;
        for (i = 0; i < length-1; i++) {
            double gain_ratio = gain_ratio(S, attributes[i]);
            if (gain_ratio > max) {
                max = gain_ratio;
                maxI = i;
            }
        }
        return attributes[maxI];
    }

    static List<String[]> getDataList(List<String[]> S, String attribute, String value){
        int index = getAttributeIndex(attribute);
        List<String[]> dataList = new ArrayList<>();
        for (String[] data : S) {
            if (value .equals(data[index])){
              dataList.add(data);
            }
        }
        return dataList;
    }

    private static Map<String,Integer> getAttributeCount(List<String[]> dataList, String attribute){
        int index = getAttributeIndex(attribute);
        Map<String,Integer> attributeMap = new HashMap<>();
        for (String[] data : dataList) {
            if (attributeMap.containsKey(data[index]))
                attributeMap.put(data[index], attributeMap.get(data[index])+1);
            else
                 attributeMap.put(data[index],1);
        }
        return attributeMap;
    }

    private static int getAttributeIndex(String attribute){
        return Arrays.asList(attributes).indexOf(attribute);
    }

    static boolean isTheSameClass(List<String[]> S){
        int length = S.get(0).length;
        String cls = S.get(0)[length - 1];
        for (String[] s : S) {
            if (!cls.equals(s[length - 1])){
               return false;
            }
        }
        return true;
    }

    /**
     * 得到map 属性：属性值list
     * @param map
     */
    private static void setAttribute_Value(Map<String, List<String>> map){
        List<String> list;
        for (String atb : attributes) {
            list = new ArrayList<>();
            int index = getAttributeIndex(atb);
            for (String[] data : datas) {
                if (list.indexOf(data[index]) == -1){
                    list.add(data[index]);
                }
            }
            map.put(atb, list);
        }
    }

    static void printTree(Tree tree, int depth){
        for (int i = 0; i < depth; i++) { System.out.print("\t"); }
        if (tree.getArrived_attribute() != null){
            System.out.println(tree.getArrived_attribute());
            for (int i = 0; i < depth + 1; i++) { System.out.print("\t"); }
        }
            System.out.println(tree.getAttribute());
            List<Tree> children = tree.getChildren();
            for (Tree child : children) {
                printTree(child,depth+1);
            }
    }

    static List<String[]> getDatas() {
        return datas;
    }
}
