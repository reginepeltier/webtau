package formHandlers

import org.openqa.selenium.Keys
import org.testingisdocumenting.webtau.browser.page.HtmlNode
import org.testingisdocumenting.webtau.browser.page.PageElement
import org.testingisdocumenting.webtau.browser.page.PageElementStepExecutor
import org.testingisdocumenting.webtau.browser.handlers.PageElementGetSetValueHandler
import org.testingisdocumenting.webtau.reporter.TokenizedMessage

class CustomInput implements PageElementGetSetValueHandler {
    @Override
    boolean handles(HtmlNode htmlNode, PageElement pageElement) {
        return htmlNode.attributes.class =~ /special-selector/
    }

    @Override
    void setValue(PageElementStepExecutor stepExecutor,
                  TokenizedMessage pathDescription,
                  HtmlNode htmlNode,
                  PageElement pageElement,
                  Object value) {
        pageElement.click()
        pageElement.find('input').sendKeys("${value}" + Keys.TAB)
    }

    @Override
    Object getValue(HtmlNode htmlNode, PageElement pageElement) {
        return pageElement.find('.current-value').getUnderlyingValue()
    }
}
