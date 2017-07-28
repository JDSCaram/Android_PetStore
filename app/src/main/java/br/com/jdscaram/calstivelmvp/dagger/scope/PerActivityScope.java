package br.com.jdscaram.calstivelmvp.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * {Created by Jonatas Caram on 22/06/2017.
 * jcaram@luxfacta.com
 * For Luxfacta Soluções de TI.
 * {@see more in https://www.luxfacta.com}.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivityScope {
}
