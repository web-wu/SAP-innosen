package com.tabwu.SAP.attendance.service;

/**
 * @PROJECT_NAME: SAP-innosen
 * @USER: tabwu
 * @DATE: 2022/10/28 15:26
 * @DESCRIPTION:
 */
public interface SignIn {

    int signIn(String id);

    int getSignIn(String id);

    Boolean checkStatusByDayOfMouth(String id, int dayOfMouth);

    int resignIn(String id, int dayOfMouth);

    Integer countSignIn(String id);

    int signCount(String id);

    int getFirstSign(String id);
}
