/* Generated By:JavaCC: Do not edit this line. ADQLParserConstants.java */
package adql.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ADQLParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int SQL_RESERVED_WORD = 2;
  /** RegularExpression Id. */
  int LEFT_PAR = 3;
  /** RegularExpression Id. */
  int RIGHT_PAR = 4;
  /** RegularExpression Id. */
  int DOT = 5;
  /** RegularExpression Id. */
  int COMMA = 6;
  /** RegularExpression Id. */
  int EOQ = 7;
  /** RegularExpression Id. */
  int CONCAT = 8;
  /** RegularExpression Id. */
  int PLUS = 9;
  /** RegularExpression Id. */
  int MINUS = 10;
  /** RegularExpression Id. */
  int ASTERISK = 11;
  /** RegularExpression Id. */
  int DIVIDE = 12;
  /** RegularExpression Id. */
  int LOGIC_AND = 13;
  /** RegularExpression Id. */
  int LOGIC_OR = 14;
  /** RegularExpression Id. */
  int EQUAL = 15;
  /** RegularExpression Id. */
  int NOT_EQUAL = 16;
  /** RegularExpression Id. */
  int LESS_THAN = 17;
  /** RegularExpression Id. */
  int LESS_EQUAL_THAN = 18;
  /** RegularExpression Id. */
  int GREATER_THAN = 19;
  /** RegularExpression Id. */
  int GREATER_EQUAL_THAN = 20;
  /** RegularExpression Id. */
  int SELECT = 21;
  /** RegularExpression Id. */
  int QUANTIFIER = 22;
  /** RegularExpression Id. */
  int TOP = 23;
  /** RegularExpression Id. */
  int FROM = 24;
  /** RegularExpression Id. */
  int AS = 25;
  /** RegularExpression Id. */
  int NATURAL = 26;
  /** RegularExpression Id. */
  int INNER = 27;
  /** RegularExpression Id. */
  int OUTER = 28;
  /** RegularExpression Id. */
  int RIGHT = 29;
  /** RegularExpression Id. */
  int LEFT = 30;
  /** RegularExpression Id. */
  int FULL = 31;
  /** RegularExpression Id. */
  int JOIN = 32;
  /** RegularExpression Id. */
  int ON = 33;
  /** RegularExpression Id. */
  int USING = 34;
  /** RegularExpression Id. */
  int WHERE = 35;
  /** RegularExpression Id. */
  int AND = 36;
  /** RegularExpression Id. */
  int OR = 37;
  /** RegularExpression Id. */
  int NOT = 38;
  /** RegularExpression Id. */
  int IS = 39;
  /** RegularExpression Id. */
  int NULL = 40;
  /** RegularExpression Id. */
  int BETWEEN = 41;
  /** RegularExpression Id. */
  int LIKE = 42;
  /** RegularExpression Id. */
  int IN = 43;
  /** RegularExpression Id. */
  int EXISTS = 44;
  /** RegularExpression Id. */
  int BY = 45;
  /** RegularExpression Id. */
  int GROUP = 46;
  /** RegularExpression Id. */
  int HAVING = 47;
  /** RegularExpression Id. */
  int ORDER = 48;
  /** RegularExpression Id. */
  int ASC = 49;
  /** RegularExpression Id. */
  int DESC = 50;
  /** RegularExpression Id. */
  int AVG = 51;
  /** RegularExpression Id. */
  int MAX = 52;
  /** RegularExpression Id. */
  int MIN = 53;
  /** RegularExpression Id. */
  int SUM = 54;
  /** RegularExpression Id. */
  int COUNT = 55;
  /** RegularExpression Id. */
  int BOX = 56;
  /** RegularExpression Id. */
  int CENTROID = 57;
  /** RegularExpression Id. */
  int CIRCLE = 58;
  /** RegularExpression Id. */
  int POINT = 59;
  /** RegularExpression Id. */
  int POLYGON = 60;
  /** RegularExpression Id. */
  int REGION = 61;
  /** RegularExpression Id. */
  int CONTAINS = 62;
  /** RegularExpression Id. */
  int INTERSECTS = 63;
  /** RegularExpression Id. */
  int AREA = 64;
  /** RegularExpression Id. */
  int COORD1 = 65;
  /** RegularExpression Id. */
  int COORD2 = 66;
  /** RegularExpression Id. */
  int COORDSYS = 67;
  /** RegularExpression Id. */
  int DISTANCE = 68;
  /** RegularExpression Id. */
  int ABS = 69;
  /** RegularExpression Id. */
  int CEILING = 70;
  /** RegularExpression Id. */
  int DEGREES = 71;
  /** RegularExpression Id. */
  int EXP = 72;
  /** RegularExpression Id. */
  int FLOOR = 73;
  /** RegularExpression Id. */
  int LOG = 74;
  /** RegularExpression Id. */
  int LOG10 = 75;
  /** RegularExpression Id. */
  int MOD = 76;
  /** RegularExpression Id. */
  int PI = 77;
  /** RegularExpression Id. */
  int POWER = 78;
  /** RegularExpression Id. */
  int RADIANS = 79;
  /** RegularExpression Id. */
  int RAND = 80;
  /** RegularExpression Id. */
  int ROUND = 81;
  /** RegularExpression Id. */
  int SQRT = 82;
  /** RegularExpression Id. */
  int TRUNCATE = 83;
  /** RegularExpression Id. */
  int ACOS = 84;
  /** RegularExpression Id. */
  int ASIN = 85;
  /** RegularExpression Id. */
  int ATAN = 86;
  /** RegularExpression Id. */
  int ATAN2 = 87;
  /** RegularExpression Id. */
  int COS = 88;
  /** RegularExpression Id. */
  int COT = 89;
  /** RegularExpression Id. */
  int SIN = 90;
  /** RegularExpression Id. */
  int TAN = 91;
  /** RegularExpression Id. */
  int STRING_LITERAL = 95;
  /** RegularExpression Id. */
  int SCIENTIFIC_NUMBER = 96;
  /** RegularExpression Id. */
  int UNSIGNED_FLOAT = 97;
  /** RegularExpression Id. */
  int UNSIGNED_INTEGER = 98;
  /** RegularExpression Id. */
  int DIGIT = 99;
  /** RegularExpression Id. */
  int DELIMITED_IDENTIFIER = 102;
  /** RegularExpression Id. */
  int REGULAR_IDENTIFIER_CANDIDATE = 103;
  /** RegularExpression Id. */
  int Letter = 104;

  /** Lexical state. */
  int DEFAULT = 0;
  /** Lexical state. */
  int WithinString = 1;
  /** Lexical state. */
  int WithinDelimitedId = 2;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "<token of kind 1>",
    "<SQL_RESERVED_WORD>",
    "\"(\"",
    "\")\"",
    "\".\"",
    "\",\"",
    "\";\"",
    "\"||\"",
    "\"+\"",
    "\"-\"",
    "\"*\"",
    "\"/\"",
    "\"&\"",
    "\"|\"",
    "\"=\"",
    "<NOT_EQUAL>",
    "\"<\"",
    "\"<=\"",
    "\">\"",
    "\">=\"",
    "\"SELECT\"",
    "<QUANTIFIER>",
    "\"TOP\"",
    "\"FROM\"",
    "\"AS\"",
    "\"NATURAL\"",
    "\"INNER\"",
    "\"OUTER\"",
    "\"RIGHT\"",
    "\"LEFT\"",
    "\"FULL\"",
    "\"JOIN\"",
    "\"ON\"",
    "\"USING\"",
    "\"WHERE\"",
    "\"AND\"",
    "\"OR\"",
    "\"NOT\"",
    "\"IS\"",
    "\"NULL\"",
    "\"BETWEEN\"",
    "\"LIKE\"",
    "\"IN\"",
    "\"EXISTS\"",
    "\"BY\"",
    "\"GROUP\"",
    "\"HAVING\"",
    "\"ORDER\"",
    "\"ASC\"",
    "\"DESC\"",
    "\"AVG\"",
    "\"MAX\"",
    "\"MIN\"",
    "\"SUM\"",
    "\"COUNT\"",
    "\"BOX\"",
    "\"CENTROID\"",
    "\"CIRCLE\"",
    "\"POINT\"",
    "\"POLYGON\"",
    "\"REGION\"",
    "\"CONTAINS\"",
    "\"INTERSECTS\"",
    "\"AREA\"",
    "\"COORD1\"",
    "\"COORD2\"",
    "\"COORDSYS\"",
    "\"DISTANCE\"",
    "\"ABS\"",
    "\"CEILING\"",
    "\"DEGREES\"",
    "\"EXP\"",
    "\"FLOOR\"",
    "\"LOG\"",
    "\"LOG10\"",
    "\"MOD\"",
    "\"PI\"",
    "\"POWER\"",
    "\"RADIANS\"",
    "\"RAND\"",
    "\"ROUND\"",
    "\"SQRT\"",
    "\"TRUNCATE\"",
    "\"ACOS\"",
    "\"ASIN\"",
    "\"ATAN\"",
    "\"ATAN2\"",
    "\"COS\"",
    "\"COT\"",
    "\"SIN\"",
    "\"TAN\"",
    "<token of kind 92>",
    "\"\\\'\"",
    "<token of kind 94>",
    "\"\\\'\"",
    "<SCIENTIFIC_NUMBER>",
    "<UNSIGNED_FLOAT>",
    "<UNSIGNED_INTEGER>",
    "<DIGIT>",
    "\"\\\"\"",
    "<token of kind 101>",
    "\"\\\"\"",
    "<REGULAR_IDENTIFIER_CANDIDATE>",
    "<Letter>",
  };

}
