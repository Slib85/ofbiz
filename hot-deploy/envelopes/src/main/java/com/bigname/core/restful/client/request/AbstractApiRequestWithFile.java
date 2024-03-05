package com.bigname.core.restful.client.request;

import java.io.File;

/**
 * Created by Manu on 2/13/2017.
 */
abstract public class AbstractApiRequestWithFile extends AbstractApiRequest implements ApiRequest {
    private File file;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        checkRequiredArgument(file, "file");
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractApiRequestWithFile that = (AbstractApiRequestWithFile) o;

        return file != null ? file.equals(that.file) : that.file == null;

    }

    @Override
    public int hashCode() {
        return file != null ? file.hashCode() : 0;
    }
}
