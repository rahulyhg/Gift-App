/*
 * Project home: https://github.com/jaxio/celerio-angular-quickstart
 * 
 * Source code generated by Celerio, an Open Source code generator by Jaxio.
 * Documentation: http://www.jaxio.com/documentation/celerio/
 * Source code: https://github.com/jaxio/celerio/
 * Follow us on twitter: @jaxiosoft
 * This header can be customized in Celerio conf...
 * Template pack-angular:src/main/java/domain/EntityMeta_.java.e.vm
 */
package com.willbe.giftapp.domain;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(App_.class)
public abstract class App__ {

    // Raw attributes
    public static volatile SingularAttribute<App_, Integer> id;
    public static volatile SingularAttribute<App_, String> category_;
    public static volatile SingularAttribute<App_, String> templatePath;

    // One to many
    public static volatile ListAttribute<App_, AppWidget> appWidgets;
}