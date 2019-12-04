package com.alexauu;


import java.util.List;

public class C45 {

    static void getC45Report() throws Exception {
        utils.importData();
        utils.printTree(generateTreeByC45(utils.getDatas(),null),0);
    }

    private static Tree generateTreeByC45(List<String[]> S, String arrived_attribute){
        if (S == null || S.size() <= 0) return new Tree(utils.clazz[1],arrived_attribute);
        if (utils.isTheSameClass(S)) { return new Tree(S.get(0)[S.get(0).length - 1],arrived_attribute); }
        Tree node = new Tree();
        String leafAttribute = utils.getNodeByC45(S);
        node.setAttribute(leafAttribute);
        node.setArrived_attribute(arrived_attribute);
        List<String> valueList = utils.attribute_value.get(leafAttribute);
        for (String value : valueList) {
            node.addNode(generateTreeByC45(utils.getDataList(S, leafAttribute, value), value));
        }
        return node;
    }
}
