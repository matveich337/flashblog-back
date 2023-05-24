*** Settings ***
Library  SeleniumLibrary

*** Variables ***
${BROWSER}   chrome
${SELSPEED}  0.2s

*** Test Cases ***
registration
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    click    id=header-register-button
    click    id=header-register-button
    type    id=email    test@test
    type    id=username    test
    type    id=password    test
    type    id=confirmPassword    test
    click    id=registerButton
    [Teardown]  Close Browser

password-restore
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    click    id=forgotPassword
    click    id=email
    type    id=email    test@test
    click    id=submitEmail
    [Teardown]  Close Browser

loggining
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    click    id=email
    type    id=email    test@test
    click    id=password
    type    id=password    test
    click    id=loginButton
    [Teardown]  Close Browser

my-profile
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    login  test@test  test
    click    id=myProfile
    click    id=modifyInfoButton
    click    id=modifyDataButton
    [Teardown]  Close Browser

my-profile-data-change
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    login  test@test  test
    click    id=myProfile
    click    id=modifyPasswordButton
    type    id=password    test
    type    id=confirmPassword    test
    click    css=.css-k008qs > #modifyPasswordButton
    [Teardown]  Close Browser

my-profile-password-change
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    login  test@test  test
    click    id=newPostButton
    type    id=postTheme    Test
    click    id=postContent
    type    id=postContent    Test
    click    id=createPostButton
    [Teardown]  Close Browser

create-post
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    login  test@test  test
    click    id=newPostButton
    type    id=postTheme    Test
    click    id=postContent
    type    id=postContent    Test
    click    id=createPostButton
    [Teardown]  Close Browser

comment-post
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    login  test@test  test
    click    id=postsButtonz
    click    xpath=//div[@id='detailsButton']/div/div[3]/div[2]
    click    id=commentary
    type    id=commentary    test
    click    id=commentarySubmitButton
    click    css=.post-page-main
    [Teardown]  Close Browser

logout
    [Setup]  Run Keywords  Open Browser  http://localhost:3000/  ${BROWSER}
    ...              AND   Set Selenium Speed  ${SELSPEED}
    login  test@test  test
    click    id=header-log-oug-button
    click    css=.login-main
    click    id=header-login-button
    click    css=.login-main
    [Teardown]  Close Browser

*** Keywords ***
login
    [Arguments]    ${email}    ${pass}
        click    id=email
        type    id=email    ${email}
        click    id=password
        type    id=password    ${pass}
        click    id=loginButton
open
    [Arguments]    ${element}
    Go To          ${element}

clickAndWait
    [Arguments]    ${element}
    Click Element  ${element}

click
    [Arguments]    ${element}
    Click Element  ${element}

sendKeys
    [Arguments]    ${element}    ${value}
    Press Keys     ${element}    ${value}

submit
    [Arguments]    ${element}
    Submit Form    ${element}

type
    [Arguments]    ${element}    ${value}
    Input Text     ${element}    ${value}

selectAndWait
    [Arguments]        ${element}  ${value}
    Select From List   ${element}  ${value}

select
    [Arguments]        ${element}  ${value}
    Select From List   ${element}  ${value}

verifyValue
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

verifyText
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

verifyElementPresent
    [Arguments]                  ${element}
    Page Should Contain Element  ${element}

verifyVisible
    [Arguments]                  ${element}
    Page Should Contain Element  ${element}

verifyTitle
    [Arguments]                  ${title}
    Title Should Be              ${title}

verifyTable
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

assertConfirmation
    [Arguments]                  ${value}
    Alert Should Be Present      ${value}

assertText
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

assertValue
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

assertElementPresent
    [Arguments]                  ${element}
    Page Should Contain Element  ${element}

assertVisible
    [Arguments]                  ${element}
    Page Should Contain Element  ${element}

assertTitle
    [Arguments]                  ${title}
    Title Should Be              ${title}

assertTable
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

waitForText
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

waitForValue
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

waitForElementPresent
    [Arguments]                  ${element}
    Page Should Contain Element  ${element}

waitForVisible
    [Arguments]                  ${element}
    Page Should Contain Element  ${element}

waitForTitle
    [Arguments]                  ${title}
    Title Should Be              ${title}

waitForTable
    [Arguments]                  ${element}  ${value}
    Element Should Contain       ${element}  ${value}

doubleClick
    [Arguments]           ${element}
    Double Click Element  ${element}

doubleClickAndWait
    [Arguments]           ${element}
    Double Click Element  ${element}

goBack
    Go Back

goBackAndWait
    Go Back

runScript
    [Arguments]         ${code}
    Execute Javascript  ${code}

runScriptAndWait
    [Arguments]         ${code}
    Execute Javascript  ${code}

setSpeed
    [Arguments]           ${value}
    Set Selenium Timeout  ${value}

setSpeedAndWait
    [Arguments]           ${value}
    Set Selenium Timeout  ${value}

verifyAlert
    [Arguments]              ${value}
    Alert Should Be Present  ${value}
