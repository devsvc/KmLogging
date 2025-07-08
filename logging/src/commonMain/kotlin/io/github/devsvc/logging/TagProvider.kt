package io.github.devsvc.logging

interface TagProvider {
    fun createTag(fromClass: String?): Pair<String, String>
}