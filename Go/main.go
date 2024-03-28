package main

import (
	"fmt"
	"os"

	"github.com/siddhant-vij/Personal-Book-Library/services"
)

func main() {
	if len(os.Args) < 2 {
		fmt.Println("No arguments provided. Please use:\n--ci (for creating tables and inserting data)\n--q (for performing sql queries)")
		return
	}
	if len(os.Args) > 2 {
		fmt.Println("Invalid number of arguments.")
		return
	}
	switch os.Args[1] {
	case "--ci":
		services.CreateInsert()
	case "--q":
		services.SqlQueries()
	default:
		fmt.Println("Invalid argument.")
	}
}
