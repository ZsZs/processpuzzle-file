"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
Object.defineProperty(exports, "__esModule", { value: true });
var core_1 = require("@angular/core");
var http_1 = require("@angular/common/http");
var FileUploadComponent = /** @class */ (function () {
    function FileUploadComponent(httpClient) {
        this.httpClient = httpClient;
        this.selectedFile = null;
    }
    // public accessors and mutators
    // event handling methods
    FileUploadComponent.prototype.onFileSelected = function (event) {
        this.selectedFile = event.target.files[0];
    };
    FileUploadComponent.prototype.onUpload = function () {
        var uploadData = new FormData();
        uploadData.append('file', this.selectedFile, this.selectedFile.name);
        this.httpClient.post('', uploadData, { reportProgress: true, observe: 'events' }).subscribe(function (event) {
            if (event.type === http_1.HttpEventType.UploadProgress) {
                console.log('Upload Progress: ' + Math.round(event.loaded / event.total) * 100 + '%');
            }
            else if (event.type === http_1.HttpEventType.Response) {
                console.log(event);
            }
        });
    };
    FileUploadComponent = __decorate([
        core_1.Component({
            selector: 'file-upload-component',
            templateUrl: './file-upload.component.html'
        }),
        __metadata("design:paramtypes", [http_1.HttpClient])
    ], FileUploadComponent);
    return FileUploadComponent;
}());
exports.FileUploadComponent = FileUploadComponent;
//# sourceMappingURL=file-upload.component.js.map