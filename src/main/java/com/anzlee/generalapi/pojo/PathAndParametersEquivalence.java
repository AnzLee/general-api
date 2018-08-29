/**
 * PathAndParametersEquivalence
 *
 * @author li.liangzhe
 * @create 2018-02-01 15:45
 **/
package com.anzlee.generalapi.pojo;

import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Sets;
import springfox.documentation.RequestHandler;
import springfox.documentation.service.ResolvedMethodParameter;

import java.util.List;
import java.util.Set;

class PathAndParametersEquivalence extends Equivalence<RequestHandler> {
    private static final ResolvedMethodParameterEquivalence parameterEquivalence
            = new ResolvedMethodParameterEquivalence();

    @Override
    protected boolean doEquivalent(RequestHandler a, RequestHandler b) {
        return a.getPatternsCondition().equals(b.getPatternsCondition())
                && !Sets.intersection(a.supportedMethods(), b.supportedMethods()).isEmpty()
                && a.params().equals(b.params())
                && Sets.difference(wrapped(a.getParameters()), wrapped(b.getParameters())).isEmpty();
    }

    private Set<Wrapper<ResolvedMethodParameter>> wrapped(List<ResolvedMethodParameter> parameters) {
        return FluentIterable.from(parameters)
                .transform(wrappingFunction())
                .toSet();
    }

    private Function<ResolvedMethodParameter, Wrapper<ResolvedMethodParameter>> wrappingFunction() {
        return new Function<ResolvedMethodParameter, Wrapper<ResolvedMethodParameter>>() {
            @Override
            public Wrapper<ResolvedMethodParameter> apply(ResolvedMethodParameter input) {
                return parameterEquivalence.wrap(input);
            }
        };
    }

    @Override
    protected int doHash(RequestHandler requestHandler) {
        return Objects.hashCode(
                requestHandler.getPatternsCondition().getPatterns(),
                requestHandler.supportedMethods(),
                requestHandler.params(),
                wrapped(requestHandler.getParameters()));
    }
}

