package com.alexauu;

import java.util.List;

class ID3 {

    static void getID3Report() throws Exception {
        utils.importData();
        utils.printTree(generateTreeByID3(utils.getDatas(),null),0);
    }

    private static Tree generateTreeByID3(List<String[]> S, String arrived_attribute){
        if (S == null || S.size() <= 0) return new Tree(utils.clazz[1],arrived_attribute);
        if (utils.isTheSameClass(S)) { return new Tree(S.get(0)[S.get(0).length - 1],arrived_attribute); }
        Tree node = new Tree();
        String leafAttribute = utils.getNodeByID3(S);
        System.out.println(leafAttribute);
        node.setAttribute(leafAttribute);
        node.setArrived_attribute(arrived_attribute);
        List<String> valueList = utils.attribute_value.get(leafAttribute);

        for (String value : valueList) {
            node.addNode(generateTreeByID3(utils.getDataList(S, leafAttribute, value), value));
        }
        return node;
    }
}
