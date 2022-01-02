# schist

A tool for maintaining command history in a unified, persistent way across choices of shell/REPL and devices.

The schema for what is tracked is

| id | command-text | execution-datetime-lowerbound | execution-datetime-upperbound | application | bookmarked | annotation | media | device | version

I should have my ID as UUIDs so that merge is trivial.

## Usage

"schist", with a "sht" alias, is a command-line utility with subcommands

### search

### merge



## Dependencies

* java 8+
* sqlite
* 
## License

https://www.mozilla.org/en-US/MPL/2.0/


