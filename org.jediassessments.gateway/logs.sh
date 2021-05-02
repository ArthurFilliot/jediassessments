#!/usr/bin/env bash

PS3='Select a container : ' 
## Collect the files in the array $files
output=$(docker ps -s --format '{{.ID}} - {{.Image}}' | sed -e :a -e 'N;s/\n/;/; ta')
IFS=';' read -ra files <<< "$output"

## Enable extended globbing. This lets us use @(foo|bar) to
## match either 'foo' or 'bar'.
shopt -s extglob

## Start building the string to match against.
string="@(${files[0]}"
## Add the rest of the files to the string
for((i=1;i<${#files[@]};i++))
do
    string+="|${files[$i]}"
done
## Close the parenthesis. $string is now @(file1|file2|...|fileN)
string+=")"

## Show the menu. This will list all files and the string "quit"
select file in "${files[@]}" "quit"
do
    case $file in
    ## If the choice is one of the files (if it matches $string)
    $string)
        ## Do something here
        ## echo "$file"
        ## Uncomment this line if you don't want the menu to
        ## be shown again
	IFS=' - ' read -ra tokens <<< "$file"
	docker logs $tokens --follow
        break;
        ;;

    "quit")
        ## Exit
        exit;;
    *)
        file=""
        echo "Please choose a number from 1 to $((${#files[@]}+1))";;
    esac
done
