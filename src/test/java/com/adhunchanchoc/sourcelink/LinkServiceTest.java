package com.adhunchanchoc.sourcelink;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class LinkServiceTest {
    private String url;
    private String output;
    @Autowired //field injection
    private static LinkService ls;
//    static final Logger log = Logger.getLogger(TestLifecycleLogger.class.getName()); //using java.util.Logger

    @BeforeAll
    static void initAll(){
            log.info("LinkServiceTest started");
    }

    @BeforeEach
    void cleanStrings() {
        url = output = "";
    }
    @Test
    void shouldMakeUrlCompatible() {
        url = "https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html";
        output = "docs.spring.io_spring-framework_reference_web_webmvc_mvc-controller_ann-requestmapping.html";
        Assertions.assertEquals(output, ls.makeUrlCompatible(url));
    }
    @Test
    void shouldTrimNextParameters() {
        url = "https://www.google.com/search?q=SEARCHED+WORD&sca_esv=580414175&ei=mFRLZfqEIs2M9u8P1qyRkAk&ved=0ahUKEwj6sNOci7SCAxVNhv0HHVZWBJIQ4dUDCBA&uact=5&oq=SEARCHED+WORD&gs_lp=Egxnd3Mtd2l6LXNlcnAiDVNFQVJDSEVEIFdPUkQyBRAAGIAEMgYQABgWGB4yBhAAGBYYHjIGEAAYFhgeMgYQABgWGB4yBhAAGBYYHjIGEAAYFhgeMgYQABgWGB4yBhAAGBYYHjIGEAAYFhgeSJ2MAVDkEliMgwFwAngCkAEAmAFwoAGyCKoBBDEyLjG4AQPIAQD4AQHCAgQQABhHwgIKEAAYRxjWBBiwA8ICERAuGIAEGLEDGIMBGMcBGNEDwgILEAAYgAQYsQMYgwHCAgsQABiKBRixAxiDAcICCxAuGIAEGMcBGNEDwgIgEC4YgAQYsQMYgwEYxwEY0QMYlwUY3AQY3gQY4ATYAQHCAggQABiABBixA8ICCxAuGIAEGLEDGIMBwgIFEC4YgATCAg4QLhiABBixAxjHARjRA8ICCBAuGIAEGLEDwgILEC4YgAQYxwEYrwHCAgcQABiABBgKwgIKEAAYgAQYsQMYCsICBxAuGIAEGArCAhQQLhiABBiXBRjcBBjeBBjgBNgBAcICDRAAGIAEGLEDGIMBGArCAhYQLhiABBgKGJcFGNwEGN4EGOAE2AEBwgIKEAAYFhgeGA8YCsICCBAAGBYYHhgK4gMEGAAgQYgGAZAGCLoGBggBEAEYFA&sclient=gws-wiz-serp";
        output = "google.com_search_q_SEARCHED_WORD_sca_esv_580414175_ei_mFRLZfqEIs2M9u8P1qyRkAk_ved_0ahUKEwj6sNOci7SCAxVNhv0HHVZWBJIQ4dUDCBA_uact_5_oq_SEARCH";
        Assertions.assertEquals(output, ls.makeUrlCompatible(url));
    }
    @Test
    void shouldMakeNameShorter(){
        // 150 characters (URLs can get up to 2000)
        url="https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/context/support/AbstractRefreshableWebApplicationContext.html";
//      output = "docs.spring.io_spring-framework_docs_current_javadoc-api_org_springframework_web_context_support_AbstractRefreshableWebApplicationContext.ht";
        Assertions.assertTrue(ls.makeUrlCompatible(url).length() <=140);
    }
    @AfterAll
    static void tearDownAll() {
        ls = null;
        log.info("LinkServiceTest finished");
    }
}