/**
 * ResolvedMethodParameterEquivalence
 *
 * @author li.liangzhe
 * @create 2018-02-01 15:46
 **/
package com.anzlee.generalapi.pojo;


import com.google.common.base.Equivalence;
import com.google.common.base.Objects;
import springfox.documentation.service.ResolvedMethodParameter;

class ResolvedMethodParameterEquivalence extends Equivalence<ResolvedMethodParameter> {
    @Override
    protected boolean doEquivalent(ResolvedMethodParameter a, ResolvedMethodParameter b) {
        return Objects.equal(a.defaultName(), b.defaultName())
                && Objects.equal(a.getParameterIndex(), b.getParameterIndex())
                && Objects.equal(a.getParameterType(), b.getParameterType());
    }

    @Override
    protected int doHash(ResolvedMethodParameter self) {
        return Objects.hashCode(
                self.defaultName(),
                self.getParameterIndex(),
                self.getParameterType());
    }
}

