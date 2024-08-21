package com.haniel.cinder.model

class NullUser : User("NOT FOUND", "NOT FOUND",-1,0,"USER NOT FOUND") {

    override fun isEmpty(): Boolean {
        return true;
    }
}