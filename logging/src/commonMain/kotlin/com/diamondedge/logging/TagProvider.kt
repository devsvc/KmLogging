package com.diamondedge.logging

interface TagProvider {
    fun createTag(fromClass: String?): Pair<String, String>
}