grammar Logic;

@header {
    package antlr4;
}

start: e=expr EOF;

expr : '(' c1=expr ')'                  # Parentheses
     | '~' c1=expr                      # NOT
     | c1=expr ('<=>') c2=expr          # IFF
     | c1=expr ('=>') c2=expr           # IMP
     | c1=expr ('&') c2=expr            # AND
     | c1=expr ('|') c2=expr            # OR
     | x=ID                             # Atomic
     ;

ID: [A-Z] ;
WS: [ \t\r\n]+ -> skip ;