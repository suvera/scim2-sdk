# ANTLR Parser generator

This module contains the ANTLR generated parser for the SCIM2 filter expression.

## How to generate the parser

1. Install ANTLR4
2. Run the following command to generate the parser:

```shell
antlr4 SCIMFilter.g4 -package dev.suvera.scim2.schema.filter.parser
```