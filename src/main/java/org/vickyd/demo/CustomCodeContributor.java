package org.vickyd.demo;

import com.intellij.psi.*;
import com.intellij.psi.impl.light.LightFieldBuilder;
import com.intellij.psi.impl.light.LightPsiClassBuilder;
import com.intellij.psi.scope.ElementClassHint;
import com.intellij.psi.scope.NameHint;
import com.intellij.psi.scope.PsiScopeProcessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.resolve.NonCodeMembersContributor;


public class CustomCodeContributor extends NonCodeMembersContributor {
  public void processDynamicElements(@NotNull PsiType qualifierType, PsiClass aClass,
      @NotNull PsiScopeProcessor processor, @NotNull PsiElement place, @NotNull ResolveState state) {
    if (aClass == null) {
      return;
    }
    final ElementClassHint hint = processor.getHint(ElementClassHint.KEY);
    if (hint != null && !hint.shouldProcess(ElementClassHint.DeclarationKind.FIELD)) {
      return;
    }

    NameHint nameHint = processor.getHint(NameHint.KEY);
    String nameHintValue = nameHint != null ? nameHint.getName(state) : null;
    if (nameHintValue == null) {
      LightFieldBuilder someInt = new LightFieldBuilder("someInt", Integer.class.getName(), aClass);
      processor.execute(someInt, state);
    }
    if ("spec".equals(nameHintValue)) {
      LightFieldBuilder someInt = new LightFieldBuilder("spec", createMyType(place), aClass);
      processor.execute(someInt, state);
    }
  }

  private PsiClassType createMyType(PsiElement context) {
    PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(context.getProject());
    PsiType type = JavaPsiFacade.getElementFactory(context.getProject())
        .createTypeFromText(CommonClassNames.JAVA_LANG_STRING, context);
    LightPsiClassBuilder myClass = new LightMyClassBuilder("MyName", context,
        new LightFieldBuilder(PsiManager.getInstance(context.getProject()), "name123", type),
        new LightFieldBuilder(PsiManager.getInstance(context.getProject()), "version123", type));
    return elementFactory.createType(myClass);
  }

  private static class LightMyClassBuilder extends LightPsiClassBuilder {
    private static final long serialVersionUID = 2595173756750630265L;
    private final PsiField[] fields;

    public LightMyClassBuilder(String name, PsiElement context, PsiField... fields) {
      super(context, name);
      this.fields = fields;
    }

    @NotNull
    @Override
    public PsiField[] getFields() {
      return fields != null ? fields : super.getFields();
    }

    //We override this method to remove all methods inherited from Object class
    @NotNull
    @Override
    public PsiClassType[] getSuperTypes() {
      return PsiClassType.EMPTY_ARRAY;
    }
  }
}
