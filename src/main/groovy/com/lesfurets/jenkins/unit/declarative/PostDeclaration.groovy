package com.lesfurets.jenkins.unit.declarative

import static com.lesfurets.jenkins.unit.declarative.DeclarativePipeline.executeOn

class PostDeclaration {

    Closure always
    Closure changed
    Closure success
    Closure unstable
    Closure failure

    def always(Closure closure) {
        this.always = closure
    }

    def changed(Closure closure) {
        this.changed = closure
    }

    def success(Closure closure) {
        this.success = closure
    }

    def unstable(Closure closure) {
        this.unstable = closure
    }

    def failure(Closure closure) {
        this.failure = closure
    }

    def execute(Object delegate) {
        def currentBuild = delegate.currentBuild.result
        if (this.always) {
            executeOn(delegate, this.always)
        }

        switch (currentBuild) {
            case 'SUCCESS':
                executeOn(delegate, this.success)
                break
            case 'FAILURE':
                executeOn(delegate, this.failure)
                break
            case 'UNSTABLE':
                executeOn(delegate, this.unstable)
                break
            case 'CHANGED':
                executeOn(delegate, this.changed)
                break
        }
    }

}