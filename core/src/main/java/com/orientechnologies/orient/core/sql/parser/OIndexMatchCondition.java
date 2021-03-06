/* Generated By:JJTree: Do not edit this line. OIndexMatchCondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=true,NODE_PREFIX=O,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package com.orientechnologies.orient.core.sql.parser;

import com.orientechnologies.orient.core.command.OCommandContext;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.sql.executor.OResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class OIndexMatchCondition extends OBooleanExpression{

  protected OBinaryCompareOperator operator;
  protected Boolean                between;

  protected List<OExpression> leftExpressions;
  protected List<OExpression> rightExpressions;

  public OIndexMatchCondition(int id) {
    super(id);
  }

  public OIndexMatchCondition(OrientSql p, int id) {
    super(p, id);
  }

  /**
   * Accept the visitor.
   **/
  public Object jjtAccept(OrientSqlVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }

  @Override public boolean evaluate(OIdentifiable currentRecord, OCommandContext ctx) {
    throw new UnsupportedOperationException("TODO Implement IndexMatch!!!");//TODO
  }

  @Override public boolean evaluate(OResult currentRecord, OCommandContext ctx) {
    throw new UnsupportedOperationException("TODO Implement IndexMatch!!!");//TODO
  }

  public void toString(Map<Object, Object> params, StringBuilder builder) {
    builder.append("KEY ");
    if (operator != null) {
      builder.append(operator.toString());
      builder.append(" [");
      boolean first = true;
      for (OExpression x : leftExpressions) {
        if (!first) {
          builder.append(", ");
        }
        x.toString(params, builder);
        first = false;
      }
      builder.append("]");
    } else if (Boolean.TRUE.equals(between)) {
      builder.append(" BETWEEN [");
      boolean first = true;
      for (OExpression x : leftExpressions) {
        if (!first) {
          builder.append(", ");
        }
        x.toString(params, builder);
        first = false;
      }
      builder.append("] AND [");
      first = true;
      for (OExpression x : rightExpressions) {
        if (!first) {
          builder.append(", ");
        }
        x.toString(params, builder);
        first = false;
      }
      builder.append("]");
    }
  }

  @Override public boolean supportsBasicCalculation() {
    return false;
  }

  @Override protected int getNumberOfExternalCalculations() {
    return 1;
  }

  @Override protected List<Object> getExternalCalculationConditions() {
    List<Object> result = new ArrayList<Object>();
    result.add(this);
    return result;
  }

  @Override public boolean needsAliases(Set<String> aliases) {
    if (leftExpressions != null) {
      for (OExpression exp : leftExpressions) {
        if (exp.needsAliases(aliases)) {
          return true;
        }
      }
    }
    if (rightExpressions != null) {
      for (OExpression exp : rightExpressions) {
        if (exp.needsAliases(aliases)) {
          return true;
        }
      }
    }
    return false;
  }

  @Override public OIndexMatchCondition copy() {
    OIndexMatchCondition result = new OIndexMatchCondition(-1);
    result.operator = operator == null ? null : (OBinaryCompareOperator) operator.copy();
    result.between = between;

    result.leftExpressions =
        leftExpressions == null ? null : leftExpressions.stream().map(x -> x.copy()).collect(Collectors.toList());
    result.rightExpressions =
        rightExpressions == null ? null : rightExpressions.stream().map(x -> x.copy()).collect(Collectors.toList());

    return result;
  }

  @Override public void extractSubQueries(SubQueryCollector collector) {
    if (leftExpressions != null) {
      for (OExpression exp : leftExpressions) {
        exp.extractSubQueries(collector);
      }
    }
    if (rightExpressions != null) {
      for (OExpression exp : rightExpressions) {
        exp.extractSubQueries(collector);
      }
    }
  }

  @Override public boolean refersToParent() {
    if (leftExpressions != null) {
      for (OExpression exp : leftExpressions) {
        if (exp != null && exp.refersToParent()) {
          return true;
        }
      }
    }
    if (rightExpressions != null) {
      for (OExpression exp : rightExpressions) {
        if (exp != null && exp.refersToParent()) {
          return true;
        }
      }
    }
    return false;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    OIndexMatchCondition that = (OIndexMatchCondition) o;

    if (operator != null ? !operator.equals(that.operator) : that.operator != null)
      return false;
    if (between != null ? !between.equals(that.between) : that.between != null)
      return false;
    if (leftExpressions != null ? !leftExpressions.equals(that.leftExpressions) : that.leftExpressions != null)
      return false;
    if (rightExpressions != null ? !rightExpressions.equals(that.rightExpressions) : that.rightExpressions != null)
      return false;

    return true;
  }

  @Override public int hashCode() {
    int result = operator != null ? operator.hashCode() : 0;
    result = 31 * result + (between != null ? between.hashCode() : 0);
    result = 31 * result + (leftExpressions != null ? leftExpressions.hashCode() : 0);
    result = 31 * result + (rightExpressions != null ? rightExpressions.hashCode() : 0);
    return result;
  }

  @Override public List<String> getMatchPatternInvolvedAliases() {
    return null;
  }

}
/* JavaCC - OriginalChecksum=702e9ab959e87b043b519844a7d31224 (do not edit this line) */
